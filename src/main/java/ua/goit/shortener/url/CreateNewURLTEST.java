//package ua.goit.shortener.url;
//
//import java.net.MalformedURLException;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.Random;
//
//public class CreateNewURLTEST {
//
//    public static String generateRandomString(int length) {
//        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        StringBuilder randomString = new StringBuilder();
//
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            char randomChar = characters.charAt(random.nextInt(characters.length()));
//            randomString.append(randomChar);
//        }
//
//        return randomString.toString();
//    }
//
//    public static String generateShortLink(String originalURL) {
//        String prefix = "https://shorter/t3/";
//        int randomLength = 6 + new Random().nextInt(3); // Генеруємо випадкову довжину від 6 до 8 символів
//        String randomString = generateRandomString(randomLength);
//
//        return prefix + randomString;
//    }
//    public static boolean isValidURL(String shortURL) {
//        try {
//            new URL(shortURL).toURI();
//            return true;
//        } catch (URISyntaxException exception) {
//            return false;
//        } catch (MalformedURLException exception) {
//            return false;
//        }
//    }
//    public static void main(String[] args) {
//        String originalURL = "https://docs.google.com/presentation/d/1ADnSzDGdYBi2NYST8M8Iu3wJ58zBu2Vc6Sl7xaApTBs/edit#slide=id.g17e012ce666_0_219";
//        String shortURL = generateShortLink(originalURL);
//        System.out.println("Short URL: " + shortURL);
//
//        if (isValidURL(shortURL)) {
//            System.out.print("The URL " + shortURL + " is valid");
//        }
//        else {
//            System.out.print("The URL " + shortURL + " isn't valid");
//        }
//    }
//
//}
