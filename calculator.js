document.addEventListener('DOMContentLoaded', function() {
    const calculateButton = document.getElementById('calculate');
    const numbersInput = document.getElementById('numbers');
    const typeSelect = document.getElementById('type');
    const resultBox = document.getElementById('result');

    calculateButton.addEventListener('click', async function() {
        const numbers = numbersInput.value.trim();
        
        // Валидация ввода
        if (!numbers) {
            alert('Пожалуйста, введите числа');
            return;
        }

        // Проверка формата чисел
        const numberPattern = /^[\d\s,]+$/;
        if (!numberPattern.test(numbers)) {
            alert('Пожалуйста, введите только числа, разделенные запятыми');
            return;
        }

        try {
            const response = await fetch('/web-calculator/api/calculate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    numbers: numbers,
                    type: typeSelect.value
                })
            });

            if (!response.ok) {
                throw new Error('Ошибка при вычислении');
            }

            const data = await response.json();
            resultBox.textContent = `Максимальное число: ${data.result}`;
        } catch (error) {
            resultBox.textContent = `Ошибка: ${error.message}`;
        }
    });

    // Валидация при вводе
    numbersInput.addEventListener('input', function() {
        const value = this.value.trim();
        if (value) {
            const numbers = value.split(',').map(n => n.trim());
            const isValid = numbers.every(n => !isNaN(n) && n !== '');
            if (!isValid) {
                this.setCustomValidity('Пожалуйста, введите корректные числа');
            } else {
                this.setCustomValidity('');
            }
        }
    });
}); 