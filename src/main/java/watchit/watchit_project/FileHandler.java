package watchit.watchit_project;

import java.io.*;
import java.util.*;
import static watchit.watchit_project.WatchitApp.*;
import Model.*;

public class FileHandler {
    // قراءة البيانات من الملفات
    public static void readAllData() {
        readAdmins();
        readUsers();
        readCasts();
        readDirectors();
        readMovies();
        readSubscriptions();
        readWatchRecords();
    }

    // حفظ البيانات في الملفات
    public static void writeAllData() {
        writeAdmins();
        writeUsers();
        writeCasts();
        writeDirectors();
        writeMovies();
        writeSubscriptions();
        writeWatchRecords();
    }

    // Admins
    private static void readAdmins() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("all_admins.txt"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            admins = (ArrayList<Admin>) inputStream.readObject();  // قراءة الأفلام من الملف
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeAdmins() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("all_admins.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            outputStream.writeObject(admins);  // حفظ قائمة الأفلام
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Users
    private static void readUsers() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("all_users.txt"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            users = (ArrayList<User>) inputStream.readObject();  // قراءة الأفلام من الملف
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeUsers() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("all_users.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            outputStream.writeObject(users);  // حفظ قائمة الأفلام
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Casts
    private static void readCasts() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("all_casts.txt"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            all_cast = (ArrayList<Cast>) inputStream.readObject();  // قراءة الأفلام من الملف
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeCasts() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("all_casts.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            outputStream.writeObject(all_cast);  // حفظ قائمة الأفلام
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Directors
    private static void readDirectors() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("all_directors.txt"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            all_director = (ArrayList<Director>) inputStream.readObject();  // قراءة الأفلام من الملف
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeDirectors() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("all_directors.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            outputStream.writeObject(all_director);  // حفظ قائمة الأفلام
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Subscriptions
    private static void readSubscriptions() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("all_subscription.txt"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            subscriptions = (ArrayList<Subscription>) inputStream.readObject();  // قراءة الأفلام من الملف
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeSubscriptions() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("all_subscription.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            outputStream.writeObject(subscriptions);  // حفظ قائمة الأفلام
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Movies
    private static void readMovies() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("all_movies.txt"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            all_movies = (ArrayList<Movie>) inputStream.readObject();  // قراءة الأفلام من الملف
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeMovies() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("all_movies.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            outputStream.writeObject(all_movies);  // حفظ قائمة الأفلام
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    // all Watch Records  for admin
    private static void readWatchRecords() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("all_watchRecords.txt"));
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            watchRecords = (ArrayList<UserWatchRecord>) inputStream.readObject();  // قراءة الأفلام من الملف
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeWatchRecords() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("all_watchRecords.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            outputStream.writeObject(watchRecords);  // حفظ قائمة الأفلام
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
