document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('studentModal');
    const addStudentBtn = document.getElementById('addStudent');
    const closeBtn = document.querySelector('.close');
    const studentForm = document.getElementById('studentForm');
    const studentsTableBody = document.getElementById('studentsTableBody');
    const modalTitle = document.getElementById('modalTitle');

    let editingStudentId = null;

    // Загрузка студентов при загрузке страницы
    loadStudents();

    // Открытие модального окна для добавления студента
    addStudentBtn.addEventListener('click', function() {
        editingStudentId = null;
        modalTitle.textContent = 'Добавить студента';
        studentForm.reset();
        modal.style.display = 'block';
    });

    // Закрытие модального окна
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Закрытие модального окна при клике вне его
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Обработка отправки формы
    studentForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const studentData = {
            name: document.getElementById('name').value,
            lastName: document.getElementById('lastName').value,
            firstName: document.getElementById('firstName').value,
            yearBirth: parseInt(document.getElementById('yearBirth').value)
        };

        try {
            if (editingStudentId) {
                // Обновление существующего студента
                await updateStudent(editingStudentId, studentData);
            } else {
                // Добавление нового студента
                await addStudent(studentData);
            }

            modal.style.display = 'none';
            loadStudents();
        } catch (error) {
            alert('Ошибка при сохранении студента: ' + error.message);
        }
    });

    // Загрузка списка студентов
    async function loadStudents() {
        try {
            const response = await fetch('/web-calculator/api/students');
            if (!response.ok) {
                throw new Error('Ошибка при загрузке студентов');
            }

            const students = await response.json();
            displayStudents(students);
        } catch (error) {
            alert('Ошибка при загрузке студентов: ' + error.message);
        }
    }

    // Отображение студентов в таблице
    function displayStudents(students) {
        studentsTableBody.innerHTML = '';
        students.forEach(student => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${student.name}</td>
                <td>${student.lastName}</td>
                <td>${student.firstName}</td>
                <td>${student.yearBirth}</td>
                <td class="action-buttons">
                    <button onclick="editStudent(${student.id})" title="Редактировать">✎</button>
                    <button onclick="deleteStudent(${student.id})" title="Удалить">🗑</button>
                </td>
            `;
            studentsTableBody.appendChild(row);
        });
    }

    // Добавление нового студента
    async function addStudent(studentData) {
        const response = await fetch('/web-calculator/api/students', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(studentData)
        });

        if (!response.ok) {
            throw new Error('Ошибка при добавлении студента');
        }

        return response.json();
    }

    // Обновление студента
    async function updateStudent(id, studentData) {
        const response = await fetch(`/web-calculator/api/students/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(studentData)
        });

        if (!response.ok) {
            throw new Error('Ошибка при обновлении студента');
        }

        return response.json();
    }

    // Удаление студента
    async function deleteStudent(id) {
        if (!confirm('Вы уверены, что хотите удалить этого студента?')) {
            return;
        }

        const response = await fetch(`/web-calculator/api/students/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Ошибка при удалении студента');
        }

        loadStudents();
    }

    // Редактирование студента
    window.editStudent = async function(id) {
        try {
            const response = await fetch(`/web-calculator/api/students/${id}`);
            if (!response.ok) {
                throw new Error('Ошибка при загрузке данных студента');
            }

            const student = await response.json();
            editingStudentId = id;
            modalTitle.textContent = 'Редактировать студента';

            document.getElementById('name').value = student.name;
            document.getElementById('lastName').value = student.lastName;
            document.getElementById('firstName').value = student.firstName;
            document.getElementById('yearBirth').value = student.yearBirth;

            modal.style.display = 'block';
        } catch (error) {
            alert('Ошибка при загрузке данных студента: ' + error.message);
        }
    };

    // Удаление студента (глобальная функция)
    window.deleteStudent = deleteStudent;
}); 