## Индивидуальный (1 финальный) тур олимпиады PROD

# LifestyleHUB

<img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/MainScreen.png" 
alt="MainScreen" 
width="200"/>
<img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/ToDoScreen.png" 
alt="ToDoScreen" 
width="200"/>
<img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/BudgetScreen.png" 
alt="BudgetScreen" 
width="200"/>
<img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/UserInfoScreen.png" 
alt="UserInfoScreen" 
width="200"/>

## О проекте
LifestyleHUB – это приложение для формирования собственного досуга и управления им. От поиска интересных мест и мероприятий до получения персональных рекомендаций. Основная цель - помочь клиенту найти, чем заняться в свободное время, изучить новые места и поучаствовать в активностях. Приложение предоставляет все, что поможет сделать выбор и распланировать отдых.
## Базовый функционал
   * Погода
   * Лента рекомендаций
   * Детали рекомендованных мест
   * Мой досуг(список дел)
   * Авторизация
     
## Дополнительный функционал

  * Планер бюджета
    
    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/GoalView1.jpg" 
    alt="Budget" 
    width="300"/>
    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/GoalView2.jpg" 
    alt="Budget" 
    width="300"/>

    Планирование бюджета - ключ к спокойствию, уверенности и достижению целей. Зная, куда уходят ваши деньги, вы будете чувствовать себя увереннее, сможете накопить на крупные покупки, сократить ненужные расходы и стать financially independent.

    Данная функция дает возможность поставить цель/лимит и следить за их выполнением, добавляя полученные/затраченные финансы в приложение.

  * Курс валют

    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/ValuteView.jpg" 
    alt="ValuteExchangeRate" 
    width="300"/>
    
    Знание курса валют – это ключ к грамотному управлению своими финансами, что позволяет экономить, совершать выгодные покупки, инвестировать и защищать свои сбережения.

    Данная функция получает курс USD и EUR при помощи Api ЦБ(https://www.cbr-xml-daily.ru/daily_json.js), а затем демонстрирует их пользователю. В виджете курса валют отражается также динамика валюты.

  * Unit-тесты маппера валют
  
    Тесты на целые или дробные значения валют, рост или падение валюты.

  * Приоритизация задач в "Мой досуг"

    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/ImportantTasks.png" 
    alt="ValuteExchangeRate" 
    width="300"/>
    
    Приоритизация задач – это навык, который позволяет нам управлять своим временем и достигать целей более эффективно.

    Данная функция позволяет при создании новой задачи в "Мой досуг" пометить ее как важную. Такие задачи отображаются со специальной пометкой "Важно".

  * Skeleton во время загрузки блоков рекомендации(мест рядом), фотографий, курса валют

    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/Loading.png" 
    alt="Skeleton" 
    width="150"/>

    Элементы загрузки – это не просто визуальные индикаторы, они играют важную роль в UI, информируя пользователя о происходящей загрузке, скрашивая время ожидания и визуализируя прогресс.

    Skeleton представляет собой анимированный градиент.

  * Проверка наличия сети перед получением данных

    Если подключение к интернету отсутствует, то приложение покажет ошибку получения данных в некоторых виджетах

  * Заглушки "Нет изображения" у фотографий

    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/NoImageRecommendationInfoScreen.jpg" 
    alt="NoImage" 
    width="200"/>
    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/NoImageRecommendationView.jpg" 
    alt="NoImage" 
    width="200"/>

    Сообщая пользователю о проблемах с интернетом или загрузкой изображения, вы повышаете его лояльность к вашему продукту и делаете его использование более комфортным.

    Заглушки представляют собой view с надписью "Нет изображения".

  * Иконка приложения

    <img src="https://github.com/T-e-i-l-s/ProdPersonalFinal/blob/main/screenshots/LifestyleHUB.svg" 
    alt="Icon" 
    width="200"/>

## Стек проекта
- Язык программирования: Kotlin
- Toolkit: Jetpack Compose
- Api:

  * [OpenWeatherMap](https://openweathermap.org/current)
  * [RandomUserApi](https://randomuser.me/)
  * [PlacesApi](https://location.foursquare.com/developer/reference/places-api-overview)
  * [Api ЦБ](https://www.cbr-xml-daily.ru/daily_json.js)
  
- Библиотеки:
  * Gson
  * Volley
  * Coil
