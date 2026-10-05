CREATE TABLE restaurants
(
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    address TEXT NOT NULL,
    opening_hours TEXT NOT NULL
);

CREATE TABLE app_users
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);

CREATE TABLE reviews
(
    id SERIAL PRIMARY KEY,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    summary TEXT NOT NULL,
    details TEXT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,

    CONSTRAINT fk_review_user
        FOREIGN KEY (user_id)
            REFERENCES app_users(id)
            ON DELETE CASCADE,

    CONSTRAINT fk_review_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurants(id)
            ON DELETE CASCADE
);