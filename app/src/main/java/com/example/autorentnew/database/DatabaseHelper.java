package com.example.autorentnew.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.autorentnew.models.Booking;
import com.example.autorentnew.models.Car;
import com.example.autorentnew.models.CarStat;
import com.example.autorentnew.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "autorent.db";
    private static final int DATABASE_VERSION = 4; // Увеличил версию

    // Таблицы
    public static final String TABLE_USERS = "users";
    public static final String TABLE_CARS = "cars";
    public static final String TABLE_BOOKINGS = "bookings";

    // Общие колонки
    public static final String COLUMN_ID = "id";

    // Колонки пользователей
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_MIDDLE_NAME = "middle_name";
    public static final String COLUMN_BIRTH_DATE = "birth_date";
    public static final String COLUMN_LICENSE_NUMBER = "license_number";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";

    // Колонки автомобилей
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_ENGINE_VOLUME = "engine_volume";
    public static final String COLUMN_ENGINE_TYPE = "engine_type";
    public static final String COLUMN_PRICE_PER_HOUR = "price_per_hour";
    public static final String COLUMN_PRICE_PER_DAY = "price_per_day";
    public static final String COLUMN_PRICE_PER_WEEK = "price_per_week";
    public static final String COLUMN_IS_AVAILABLE = "is_available";
    public static final String COLUMN_IMAGE_URL = "image_url";

    // Колонки бронирований
    public static final String COLUMN_CAR_ID = "car_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_TOTAL_PRICE = "total_price";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CREATED_AT = "created_at";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d("DB", "Создание таблиц...");

            // Таблица пользователей
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LOGIN + " TEXT UNIQUE,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_ROLE + " TEXT,"
                    + COLUMN_FIRST_NAME + " TEXT,"
                    + COLUMN_LAST_NAME + " TEXT,"
                    + COLUMN_MIDDLE_NAME + " TEXT,"
                    + COLUMN_BIRTH_DATE + " TEXT,"
                    + COLUMN_LICENSE_NUMBER + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_PHONE + " TEXT"
                    + ")";
            db.execSQL(CREATE_USERS_TABLE);
            Log.d("DB", "Таблица пользователей создана");

            // Таблица автомобилей
            String CREATE_CARS_TABLE = "CREATE TABLE " + TABLE_CARS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BRAND + " TEXT,"
                    + COLUMN_MODEL + " TEXT,"
                    + COLUMN_YEAR + " INTEGER,"
                    + COLUMN_ENGINE_VOLUME + " REAL,"
                    + COLUMN_ENGINE_TYPE + " TEXT,"
                    + COLUMN_PRICE_PER_HOUR + " REAL DEFAULT 0,"
                    + COLUMN_PRICE_PER_DAY + " REAL DEFAULT 0,"
                    + COLUMN_PRICE_PER_WEEK + " REAL DEFAULT 0,"
                    + COLUMN_IS_AVAILABLE + " INTEGER DEFAULT 1,"
                    + COLUMN_IMAGE_URL + " TEXT"
                    + ")";
            db.execSQL(CREATE_CARS_TABLE);
            Log.d("DB", "Таблица автомобилей создана");

            // Таблица бронирований
            String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CAR_ID + " INTEGER,"
                    + COLUMN_USER_ID + " INTEGER,"
                    + COLUMN_START_DATE + " TEXT,"
                    + COLUMN_END_DATE + " TEXT,"
                    + COLUMN_TOTAL_PRICE + " REAL,"
                    + COLUMN_STATUS + " TEXT DEFAULT 'active',"
                    + COLUMN_CREATED_AT + " TEXT DEFAULT CURRENT_TIMESTAMP"
                    + ")";
            db.execSQL(CREATE_BOOKINGS_TABLE);
            Log.d("DB", "Таблица бронирований создана");

            // Добавляем тестовые данные
            insertTestData(db);

        } catch (Exception e) {
            Log.e("DB", "Ошибка при создании таблиц: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void insertTestData(SQLiteDatabase db) {
        try {
            Log.d("DB", "Добавление тестовых данных...");

            // ===== ПОЛЬЗОВАТЕЛИ =====
            // Проверяем, есть ли пользователи
            Cursor userCheck = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS, null);
            int userCount = 0;
            if (userCheck.moveToFirst()) {
                userCount = userCheck.getInt(0);
            }
            userCheck.close();

            if (userCount == 0) {
                Log.d("DB", "Добавление пользователей...");

                // Админ
                ContentValues adminValues = new ContentValues();
                adminValues.put(COLUMN_LOGIN, "admin");
                adminValues.put(COLUMN_PASSWORD, "admin123");
                adminValues.put(COLUMN_ROLE, "admin");
                adminValues.put(COLUMN_FIRST_NAME, "Иван");
                adminValues.put(COLUMN_LAST_NAME, "Иванов");
                adminValues.put(COLUMN_EMAIL, "admin@mail.com");
                adminValues.put(COLUMN_PHONE, "+7 999 111-22-33");
                db.insert(TABLE_USERS, null, adminValues);

                // Менеджер
                ContentValues managerValues = new ContentValues();
                managerValues.put(COLUMN_LOGIN, "manager");
                managerValues.put(COLUMN_PASSWORD, "manager123");
                managerValues.put(COLUMN_ROLE, "manager");
                managerValues.put(COLUMN_FIRST_NAME, "Петр");
                managerValues.put(COLUMN_LAST_NAME, "Петров");
                managerValues.put(COLUMN_EMAIL, "manager@mail.com");
                managerValues.put(COLUMN_PHONE, "+7 999 222-33-44");
                db.insert(TABLE_USERS, null, managerValues);

                // Пользователь
                ContentValues userValues = new ContentValues();
                userValues.put(COLUMN_LOGIN, "user");
                userValues.put(COLUMN_PASSWORD, "user123");
                userValues.put(COLUMN_ROLE, "user");
                userValues.put(COLUMN_FIRST_NAME, "Сергей");
                userValues.put(COLUMN_LAST_NAME, "Сергеев");
                userValues.put(COLUMN_EMAIL, "user@mail.com");
                userValues.put(COLUMN_PHONE, "+7 999 333-44-55");
                db.insert(TABLE_USERS, null, userValues);

                Log.d("DB", "Пользователи добавлены");
            }

            // ===== АВТОМОБИЛИ =====
            // Проверяем, есть ли автомобили
            Cursor carCheck = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CARS, null);
            int carCount = 0;
            if (carCheck.moveToFirst()) {
                carCount = carCheck.getInt(0);
            }
            carCheck.close();

            if (carCount == 0) {
                Log.d("DB", "Добавление автомобилей...");

                // BMW 5
                ContentValues bmwValues = new ContentValues();
                bmwValues.put(COLUMN_BRAND, "BMW");
                bmwValues.put(COLUMN_MODEL, "5 Series");
                bmwValues.put(COLUMN_YEAR, 2020);
                bmwValues.put(COLUMN_ENGINE_VOLUME, 3.0);
                bmwValues.put(COLUMN_ENGINE_TYPE, "Бензин");
                bmwValues.put(COLUMN_PRICE_PER_HOUR, 20);
                bmwValues.put(COLUMN_PRICE_PER_DAY, 150);
                bmwValues.put(COLUMN_PRICE_PER_WEEK, 700);
                bmwValues.put(COLUMN_IS_AVAILABLE, 1);
                long bmwResult = db.insert(TABLE_CARS, null, bmwValues);
                Log.d("DB", "BMW добавлен: " + (bmwResult != -1 ? "успешно" : "ошибка"));

                // Audi Q7
                ContentValues audiValues = new ContentValues();
                audiValues.put(COLUMN_BRAND, "Audi");
                audiValues.put(COLUMN_MODEL, "Q7");
                audiValues.put(COLUMN_YEAR, 2018);
                audiValues.put(COLUMN_ENGINE_VOLUME, 3.0);
                audiValues.put(COLUMN_ENGINE_TYPE, "Дизель");
                audiValues.put(COLUMN_PRICE_PER_HOUR, 15);
                audiValues.put(COLUMN_PRICE_PER_DAY, 100);
                audiValues.put(COLUMN_PRICE_PER_WEEK, 550);
                audiValues.put(COLUMN_IS_AVAILABLE, 1);
                long audiResult = db.insert(TABLE_CARS, null, audiValues);
                Log.d("DB", "Audi добавлен: " + (audiResult != -1 ? "успешно" : "ошибка"));

                // Mercedes E
                ContentValues mercValues = new ContentValues();
                mercValues.put(COLUMN_BRAND, "Mercedes");
                mercValues.put(COLUMN_MODEL, "E-Class");
                mercValues.put(COLUMN_YEAR, 2019);
                mercValues.put(COLUMN_ENGINE_VOLUME, 2.0);
                mercValues.put(COLUMN_ENGINE_TYPE, "Бензин");
                mercValues.put(COLUMN_PRICE_PER_HOUR, 10);
                mercValues.put(COLUMN_PRICE_PER_DAY, 95);
                mercValues.put(COLUMN_PRICE_PER_WEEK, 500);
                mercValues.put(COLUMN_IS_AVAILABLE, 1);
                long mercResult = db.insert(TABLE_CARS, null, mercValues);
                Log.d("DB", "Mercedes добавлен: " + (mercResult != -1 ? "успешно" : "ошибка"));

                // Cadillac
                ContentValues cadValues = new ContentValues();
                cadValues.put(COLUMN_BRAND, "Cadillac");
                cadValues.put(COLUMN_MODEL, "Escalade");
                cadValues.put(COLUMN_YEAR, 2014);
                cadValues.put(COLUMN_ENGINE_VOLUME, 6.0);
                cadValues.put(COLUMN_ENGINE_TYPE, "Бензин");
                cadValues.put(COLUMN_PRICE_PER_HOUR, 10);
                cadValues.put(COLUMN_PRICE_PER_DAY, 80);
                cadValues.put(COLUMN_PRICE_PER_WEEK, 490);
                cadValues.put(COLUMN_IS_AVAILABLE, 1);
                long cadResult = db.insert(TABLE_CARS, null, cadValues);
                Log.d("DB", "Cadillac добавлен: " + (cadResult != -1 ? "успешно" : "ошибка"));

                Log.d("DB", "Автомобили добавлены");
            }

            // Проверка итогов
            Cursor finalCheck = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_CARS, null);
            if (finalCheck.moveToFirst()) {
                int finalCount = finalCheck.getInt(0);
                Log.d("DB", "Всего автомобилей в БД: " + finalCount);
            }
            finalCheck.close();

        } catch (Exception e) {
            Log.e("DB", "Ошибка при добавлении тестовых данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB", "Обновление базы данных с версии " + oldVersion + " до " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ----- МЕТОДЫ ДЛЯ ПОЛЬЗОВАТЕЛЕЙ -----

    public User authenticate(String login, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        try {
            Log.d("AUTH", "Поиск пользователя: " + login + " / " + password);

            String query = "SELECT * FROM " + TABLE_USERS +
                    " WHERE " + COLUMN_LOGIN + "=? AND " + COLUMN_PASSWORD + "=?";

            Cursor cursor = db.rawQuery(query, new String[]{login, password});

            if (cursor.moveToFirst()) {
                Log.d("AUTH", "Пользователь НАЙДЕН!");
                user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOGIN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MIDDLE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LICENSE_NUMBER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                );
            } else {
                Log.d("AUTH", "Пользователь НЕ НАЙДЕН");
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("AUTH", "Ошибка аутентификации: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " ORDER BY " + COLUMN_ID, null);

            while (cursor.moveToNext()) {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOGIN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MIDDLE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LICENSE_NUMBER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                );
                userList.add(user);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_LOGIN, user.getLogin());
            values.put(COLUMN_PASSWORD, user.getPassword());
            values.put(COLUMN_ROLE, user.getRole());
            values.put(COLUMN_FIRST_NAME, user.getFirstName());
            values.put(COLUMN_LAST_NAME, user.getLastName());
            values.put(COLUMN_MIDDLE_NAME, user.getMiddleName());
            values.put(COLUMN_BIRTH_DATE, user.getBirthDate());
            values.put(COLUMN_LICENSE_NUMBER, user.getLicenseNumber());
            values.put(COLUMN_EMAIL, user.getEmail());
            values.put(COLUMN_PHONE, user.getPhone());

            result = db.insert(TABLE_USERS, null, values);
            Log.d("DB", "Добавление пользователя: " + (result != -1 ? "успешно" : "ошибка"));
        } catch (Exception e) {
            Log.e("DB", "Ошибка добавления пользователя: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = 0;

        try {
            result = db.delete(TABLE_USERS, COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    // ----- МЕТОДЫ ДЛЯ АВТОМОБИЛЕЙ -----

    public List<Car> getAllCars() {
        List<Car> carList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String query = "SELECT * FROM " + TABLE_CARS + " ORDER BY " + COLUMN_ID;
            Cursor cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                Car car = new Car(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ENGINE_VOLUME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGINE_TYPE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_PER_HOUR)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_PER_DAY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_PER_WEEK)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_AVAILABLE)) == 1,
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL))
                );
                carList.add(car);
            }
            cursor.close();

            Log.d("CARS", "Загружено автомобилей: " + carList.size());
            for (Car c : carList) {
                Log.d("CARS", " - " + c.getBrand() + " " + c.getModel());
            }

        } catch (Exception e) {
            Log.e("CARS", "Ошибка загрузки автомобилей: " + e.getMessage());
            e.printStackTrace();
        }

        return carList;
    }

    public Car getCarById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Car car = null;

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARS + " WHERE " + COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                car = new Car(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ENGINE_VOLUME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENGINE_TYPE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_PER_HOUR)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_PER_DAY)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_PER_WEEK)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_AVAILABLE)) == 1,
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL))
                );
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return car;
    }

    public long addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_BRAND, car.getBrand());
            values.put(COLUMN_MODEL, car.getModel());
            values.put(COLUMN_YEAR, car.getYear());
            values.put(COLUMN_ENGINE_VOLUME, car.getEngineVolume());
            values.put(COLUMN_ENGINE_TYPE, car.getEngineType());
            values.put(COLUMN_PRICE_PER_HOUR, car.getPricePerHour());
            values.put(COLUMN_PRICE_PER_DAY, car.getPricePerDay());
            values.put(COLUMN_PRICE_PER_WEEK, car.getPricePerWeek());
            values.put(COLUMN_IS_AVAILABLE, car.isAvailable() ? 1 : 0);
            values.put(COLUMN_IMAGE_URL, car.getImageUrl());

            result = db.insert(TABLE_CARS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteCar(int carId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = 0;

        try {
            result = db.delete(TABLE_CARS, COLUMN_ID + " = ?", new String[]{String.valueOf(carId)});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    // ----- МЕТОДЫ ДЛЯ БРОНИРОВАНИЙ -----

    public long addBooking(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CAR_ID, booking.getCarId());
            values.put(COLUMN_USER_ID, booking.getUserId());
            values.put(COLUMN_START_DATE, booking.getStartDate());
            values.put(COLUMN_END_DATE, booking.getEndDate());
            values.put(COLUMN_TOTAL_PRICE, booking.getTotalPrice());
            values.put(COLUMN_STATUS, booking.getStatus());

            result = db.insert(TABLE_BOOKINGS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Booking> getUserBookings(int userId) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS +
                            " WHERE " + COLUMN_USER_ID + "=? ORDER BY " + COLUMN_ID + " DESC",
                    new String[]{String.valueOf(userId)});

            while (cursor.moveToNext()) {
                Booking booking = new Booking(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAR_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT))
                );
                bookingList.add(booking);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingList;
    }

    // ----- МЕТОДЫ ДЛЯ СТАТИСТИКИ -----

    public List<CarStat> getPopularCarsStats(String startDate, String endDate) {
        List<CarStat> stats = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String query = "SELECT c." + COLUMN_BRAND + ", c." + COLUMN_MODEL + ", " +
                    "COUNT(b." + COLUMN_ID + ") as booking_count " +
                    "FROM " + TABLE_BOOKINGS + " b " +
                    "JOIN " + TABLE_CARS + " c ON b." + COLUMN_CAR_ID + " = c." + COLUMN_ID + " " +
                    "WHERE b." + COLUMN_CREATED_AT + " BETWEEN ? AND ? " +
                    "GROUP BY c." + COLUMN_ID + " " +
                    "ORDER BY booking_count DESC";

            Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});

            while (cursor.moveToNext()) {
                CarStat stat = new CarStat();
                stat.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)));
                stat.setModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL)));
                stat.setBookingCount(cursor.getInt(cursor.getColumnIndexOrThrow("booking_count")));
                stats.add(stat);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stats;
    }

    public double getTotalProfit() {
        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;

        try {
            Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TOTAL_PRICE + ") FROM " + TABLE_BOOKINGS, null);

            if (cursor.moveToFirst()) {
                total = cursor.getDouble(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public double getProfitByPeriod(String startDate, String endDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;

        try {
            Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TOTAL_PRICE + ") FROM " + TABLE_BOOKINGS +
                            " WHERE " + COLUMN_CREATED_AT + " BETWEEN ? AND ?",
                    new String[]{startDate, endDate});

            if (cursor.moveToFirst()) {
                total = cursor.getDouble(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}