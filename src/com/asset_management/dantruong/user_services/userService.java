package com.asset_management.dantruong.user_services;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class userService implements IUserService {
    private static final String FILE_PATH = "src/com/asset_management/dantruong/user_services/loginInfor_signinInfor/users.dat";

    private static final userService instance = new userService();

    private userService(){}

    public static userService getIntanceUserService(){
        return instance;
    }

    @SuppressWarnings("unchecked")

    public Map<String, Users> loadUser() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (Map<String, Users>) ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            // Đây là trường hợp QUAN TRỌNG:
            // File chưa tồn tại (lần chạy đầu tiên) hoặc file rỗng.
            // Chúng ta trả về một HashMap rỗng để chương trình bắt đầu.
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Fatal error while reading 'database': " + e.getMessage());
            // Trong trường hợp lỗi, trả về Map rỗng để tránh sập chương trình
            return new HashMap<>();
        }
    }

    @Override
    public boolean saveUser(Map<String, Users> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
            return true;
        } catch (IOException e) {
            System.out.println("error saving user 'database': " + e.getMessage());
            return false;
        }
    }
}
