// Функція для перевірки валідності URL
function isValidURL(url) {
    try {
        new URL(url);
        return true;
    } catch (error) {
        return false;
    }
}

// Функція, яка буде визвана при відправці форми
function submitLinkForm(event) {
    event.preventDefault(); // Попереджуємо стандартну поведінку форми (перезавантаження стрінки)

    // Отримуємо дані з форми
    const originalUrl = document.getElementById('original-url').value;

    // Перевіряємо валідність URL
    if (!isValidURL(originalUrl)) {
        alert('Будь-ласка, введіть коректний URL.');
        return;
    }

    // Створюємо об'єкт даних для відправки на сервер
    const data = {
        originalUrl: originalUrl
    };

    // Відправляємо POST-запит на сервер
    fetch('/create-edit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            // Обробка відповіді від сервера
            if (data.shortUrl) {
                alert('Коротке посилання створене: ' + data.shortUrl);
                // Додаткова логика, наприклад, перенаправлення користувача
                // window.location.href = '/create-edit';
            } else {
                alert('Виникла помилка при створенні посилання.');
            }
        })
        .catch(error => {
            console.error('Помилка:', error);
        });
}

// Отримуємо форму и додаємо обробник події на відправку
const linkForm = document.getElementById('link-form');
linkForm.addEventListener('submit', submitLinkForm);
