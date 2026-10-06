INSERT INTO restaurants (title, address, opening_hours)
VALUES
    ('Le Petit Bistro', '10 Rue de Paris, Paris', '12:00-23:00'),
    ('Sakura Restaurant', '25 Avenue de France, Paris', '11:30-22:30'),
    ('Burger House', '8 Rue Victor Hugo, Paris', '11:00-00:00');

INSERT INTO app_users (name, email)
VALUES
    ('Alice', 'alice@example.com'),
    ('Bob', 'bob@example.com'),
    ('Charlie', 'charlie@example.com');

INSERT INTO reviews (rating, summary, details, user_id, restaurant_id)
VALUES
    (
        5,
        'Excellent restaurant',
        'Très bon service et plats délicieux.',
        1,
        1
    ),
    (
        4,
        'Très bon',
        'Bonne ambiance et personnel sympathique.',
        2,
        1
    ),
    (
        5,
        'Super sushi',
        'Produits frais et service rapide.',
        3,
        2
    );