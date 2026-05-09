from flask import Flask, request, Response, jsonify
import cv2
import numpy as np

app = Flask(__name__)

@app.route('/resize', methods=['POST'])
def resize():
    try:
        # Пробуем получить изображение из разных мест
        if request.data:
            file_bytes = request.data
        elif 'image' in request.files:
            file_bytes = request.files['image'].read()
        else:
            return jsonify({"error": "Нет изображения в запросе"}), 400
        
        print(f"Получено {len(file_bytes)} байт")
        
        # Декодируем изображение
        nparr = np.frombuffer(file_bytes, np.uint8)
        img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
        
        if img is None:
            return jsonify({"error": "Не удалось декодировать изображение"}), 400
        
        # Изменяем размер
        h, w = img.shape[:2]
        new_w = 100
        new_h = int(h * (new_w / w))
        resized = cv2.resize(img, (new_w, new_h))
        
        # Кодируем результат
        _, buffer = cv2.imencode('.jpg', resized)
        
        return Response(buffer.tobytes(), mimetype='image/jpeg')
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route('/grayscale', methods=['POST'])
def grayscale():
    try:
        if request.data:
            file_bytes = request.data
        elif 'image' in request.files:
            file_bytes = request.files['image'].read()
        else:
            return jsonify({"error": "Нет изображения в запросе"}), 400
        
        nparr = np.frombuffer(file_bytes, np.uint8)
        img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
        
        if img is None:
            return jsonify({"error": "Не удалось декодировать изображение"}), 400
        
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        _, buffer = cv2.imencode('.jpg', gray)
        
        return Response(buffer.tobytes(), mimetype='image/jpeg')
        
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
