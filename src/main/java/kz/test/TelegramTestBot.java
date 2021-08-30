package kz.test;

/*
* @author: Almas Turganbayev
* created: 30.08.2021
* theme: Telegram Bot Conversion Currency
* */


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TelegramTestBot extends TelegramLongPollingBot {

    private static final String USERNAME = "Test_SimpleBot";
    private static final String TOKEN = "1954263424:AAEpEPy1q6WDTFaXbMgC_2xQt5Wh50RZczE";
    private static Document doc;
    private static String doubleCheck = "([0-9]*)\\.([0-9]*)";
    private static boolean open_usd = false, open_eur = false, open_rub = false;
    List<String> days = new ArrayList<>();
    List<String> currency_to_tenge = new ArrayList<>();
    List<Double> new_list = new ArrayList<>();

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                boolean match = Pattern.matches(doubleCheck, text);
                boolean usd = false, eur = false, rub = false;

                if(open_usd){
                    usd = true;
                }else{
                    usd = false;
                }
                if(open_eur){
                    eur = true;
                }else{
                    eur = false;
                }

                if(open_rub){
                    rub = true;
                }else{
                    open_rub = false;
                }

                if (text.equals("/start")) {
                    System.out.println("Yes, started!");
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Hi, I'm Test Bot for Conversion Currency!");
                    sendMessage.setChatId(message.getChatId());


                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

                    sendMessage.setReplyMarkup(replyKeyboardMarkup);

                    replyKeyboardMarkup.setResizeKeyboard(true).setSelective(true).setOneTimeKeyboard(true);


                    List<KeyboardRow> keyboardRowList = new ArrayList<>();

                    KeyboardRow keyboardRow = new KeyboardRow();
                    keyboardRow.add("USD");
                    keyboardRow.add("EUR");
                    keyboardRow.add("RUB");

                    keyboardRowList.add(keyboardRow);

                    replyKeyboardMarkup.setKeyboard(keyboardRowList);


                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else if (text.equals("USD")) {
                    usd = true;
                    if (usd){
                        open_usd = true;
                    }else {
                        open_usd = false;
                    }
                    System.out.println("USD currency tabbed!");
                    try {
                        doc = Jsoup.connect("https://prodengi.kz/kurs-valyut/usd/za-nedelyu").get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Elements currency = doc.select("div.graphic-list");
                    SendMessage sendMessage = new SendMessage();


                    sendMessage.setText(currency.get(0).children().get(0).text().substring(0,4));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < currency.get(0).children().get(1).childrenSize(); i += 1){
                        days.add(currency.get(0).children().get(1).children().get(i).text().substring(0,5));
                    }
                    sendMessage.setText(String.valueOf(days));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    sendMessage.setText(currency.get(0).children().get(0).text().substring(5,9));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < currency.get(0).children().get(1).childrenSize(); i += 1){
                        currency_to_tenge.add(currency.get(0).children().get(1).children().get(i).text().substring(5,11));
                    }
                    sendMessage.setText(String.valueOf(currency_to_tenge));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    sendMessage.setText("Enter USD convert to KZT");
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else if (text.equals("EUR")) {
                    eur = true;
                    if (eur){
                        open_eur = true;
                    }else {
                        open_eur = false;
                    }
                    System.out.println("EUR currency tabbed!");
                    try {
                        doc = Jsoup.connect("https://prodengi.kz/kurs-valyut/eur/za-nedelyu").get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Elements currency = doc.select("div.graphic-list");
                    SendMessage sendMessage = new SendMessage();


                    sendMessage.setText(currency.get(0).children().get(0).text().substring(0,4));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < currency.get(0).children().get(1).childrenSize(); i += 1){
                        days.add(currency.get(0).children().get(1).children().get(i).text().substring(0,5));
                    }
                    sendMessage.setText(String.valueOf(days));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    sendMessage.setText(currency.get(0).children().get(0).text().substring(5,9));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < currency.get(0).children().get(1).childrenSize(); i += 1){
                        currency_to_tenge.add(currency.get(0).children().get(1).children().get(i).text().substring(5,11));
                    }
                    sendMessage.setText(String.valueOf(currency_to_tenge));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    sendMessage.setText("Enter EUR convert to KZT");
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                else if (text.equals("RUB")) {
                    rub = true;
                    if (rub){
                        open_rub = true;
                    }else {
                        open_rub = false;
                    }
                    System.out.println("RUB currency tabbed!");
                    try {
                        doc = Jsoup.connect("https://prodengi.kz/kurs-valyut/rub/za-nedelyu").get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Elements currency = doc.select("div.graphic-list");
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(currency.get(0).children().get(0).text().substring(0,4));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < currency.get(0).children().get(1).childrenSize(); i += 1){
                        days.add(currency.get(0).children().get(1).children().get(i).text().substring(0,5));
                    }
                    sendMessage.setText(String.valueOf(days));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    sendMessage.setText(currency.get(0).children().get(0).text().substring(5,9));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < currency.get(0).children().get(1).childrenSize(); i += 1){
                        currency_to_tenge.add(currency.get(0).children().get(1).children().get(i).text().substring(5,9));
                    }
                    sendMessage.setText(String.valueOf(currency_to_tenge));
                    sendMessage.setChatId(message.getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }else if(match && usd){
                    double dollar = Double.parseDouble(text);
                    for(int i = 0; i < currency_to_tenge.size(); i += 1){
                        new_list.add(dollar * Double.parseDouble(currency_to_tenge.get(i)));
                    }
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(String.valueOf(new_list));
                    sendMessage.setChatId(message.getChatId());

                    try {
                        execute(sendMessage);
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                }else if(match && eur){
                    double euro = Double.parseDouble(text);
                    for(int i = 0; i < currency_to_tenge.size(); i += 1){
                        new_list.add(euro * Double.parseDouble(currency_to_tenge.get(i)));
                    }
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(String.valueOf(new_list));
                    sendMessage.setChatId(message.getChatId());

                    try {
                        execute(sendMessage);
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                }
                else if(match && rub){
                    double rubl = Double.parseDouble(text);
                    for(int i = 0; i < currency_to_tenge.size(); i += 1){
                        new_list.add(rubl * Double.parseDouble(currency_to_tenge.get(i)));
                    }
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(String.valueOf(new_list));
                    sendMessage.setChatId(message.getChatId());

                    try {
                        execute(sendMessage);
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Error detected!");
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Unkown Command!");
                    sendMessage.setChatId(message.getChatId());

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
