---
name: restaurant-recommendation
description: Recommend restaurants from the Rating application using cuisine, average rating and review count.
---

# Restaurant Recommendation Skill

## Purpose

This skill recommends restaurants using real data from the Rating application.

It helps users discover restaurants according to:

- cuisine;
- average rating;
- number of reviews.

## When to use

Use this skill when a user asks for restaurant recommendations.

Examples:

- Recommend an Italian restaurant.
- Show me the best French restaurants.
- Find Japanese restaurants rated at least 4 stars.
- Give me three highly rated restaurants.

## Data used

The skill only uses restaurant data returned by the backend.

Available information includes:

- restaurant name;
- cuisine;
- address;
- opening hours;
- average rating;
- review count.

## Endpoint

```http
GET /api/skills/restaurants/recommend
```

## Parameters

- `cuisine`: optional cuisine filter. Use `All` for every cuisine.
- `minRating`: minimum accepted average rating.
- `limit`: maximum number of recommendations.

Example:

```http
GET /api/skills/restaurants/recommend?cuisine=Italian&minRating=4.0&limit=3
```

## Procedure

1. Retrieve restaurants from the existing Restaurant service.
2. Filter by cuisine when requested.
3. Ignore restaurants without reviews.
4. Remove restaurants below the requested minimum rating.
5. Sort restaurants by average rating from highest to lowest.
6. If two restaurants have the same rating, prefer the restaurant with more reviews.
7. Return only the requested number of results.

## Constraints

- Never invent a restaurant.
- Only use data returned by the application backend.
- Do not treat a restaurant with no reviews as badly rated.
- Return a maximum of 10 restaurants.
- If no restaurant matches the criteria, return an empty result.

## Error handling

If the backend is unavailable, the skill cannot provide recommendations.

If no restaurant matches the request, return an empty list instead of inventing alternatives.

## Example request

```text
Recommend three Italian restaurants rated at least 4 stars.
```

Expected behavior:

1. Filter restaurants with `cuisine = Italian`.
2. Keep only restaurants with `averageRating >= 4.0`.
3. Sort results by `averageRating` descending.
4. If two restaurants have the same rating, prefer the one with more reviews.
5. Return the top three restaurants.

## Example response data

```json
[
  {
    "id": 42,
    "title": "Pizzeria Popolare",
    "cuisine": "Italian",
    "averageRating": 4.5,
    "reviewCount": 4
  },
  {
    "id": 3,
    "title": "Pink Mamma",
    "cuisine": "Italian",
    "averageRating": 4.0,
    "reviewCount": 6
  }
]
```