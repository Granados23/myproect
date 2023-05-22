package config;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;

public class BotConfig extends TelegramLongPollingBot {


    @Override
    public String getBotUsername() {
        return "hoooooooooorymps_bot"; // Тут мы пишем имя бота. ВАЖНО!!! В конце имени должно стоять расширение "Bot"
    }

    @Override
    public String getBotToken() {
        return "Твой токен"; // Тут мы пишем токен
    }

    @Override                                                // Тут пишется весь функционал для бота
    public void onUpdateReceived(Update update) {
        // Эта строчка получает id пользователя. Таким образом он ведет дилог с каждым человеком отдельно
        String chatid = update.getMessage().getChatId().toString();
        //Строчка ниже может запоминать слова, которые пишет пользователь. Аналог с методом Scaner
        String text1 = update.getMessage().getText();
        // Строчкой ниже мы выделяем память под переменную текст
        SendMessage text = new SendMessage();
        // Строчка ниже отвечает за то, чтобы бот запоминал id пользователя
        text.setChatId(chatid);



        if (text1.equals("/start")){
            text.setText("Привет! В настоящее время бот может предоставлять данные по купонам из KFC и БК. Кликай по копке 'Меню' и выбери интересующие тебя купоны. ");
        }



       // Ниже у нас идет работа с парсингом и функционалом kfs

        if (text1.equals("/kfs")){

            Document el = null;
            try {
                el = Jsoup.connect("https://kfckupon.ru/").userAgent("Mozilla").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements doc = el.getElementsByClass("kupon-card-list row"); // Элемент каждого лота по отдельности
            Elements купон = doc.select("div[class=kupon-card__button]");// Номера купонов
            Elements описание = doc.select("div[class=kupon-card__name]"); // Описание купона
            Elements картинки1 = doc.select("div[class=kupon-card-img]");
            Elements каз = картинки1.select("img[src$=.png]");
            Elements цена1 = doc.select("div[class=kupon-card__price]"); // тут мы получаем чистые ссылки


                // Дальше мы создаем два индекса i и j, чтобы внедрить их в цикл while и данные из элементов выше выходили поочередно.
            int i = 0;
            int j = 0;
            int c = 0;
            int k = 0;
            // Строчкой ниже мы выводим все данные через цикл и обязательно while.
            // Из элементов мы достаем все данные и чередуем их между собой по одному.
            Метка:
            while (i < купон.size() && j < описание.size() && c < каз.size() && k < цена1.size()) {

                String купон1 = купон.get(i).text();
                String описание1 = описание.get(j).text();
                String цена = цена1.get(k).text().substring(0,4);   // Метод substring может удалить лишние символы. Первое число означает сколько символов удалить с начало строки, а второе с конца строки
                String картиночки = каз.get(c).attr("src");
                String aba = купон1 + "\n" + описание1 + "\n" + цена;

                // Строчкой ниже мы берем строчку aba и выводим из нее данные по 1 элементу цепочкой
                if (aba != null) {
                    // Строчка ниэе отвечает за то, чтобы телеграм бот делил сообщения. В условии ниже выводится только текст.
                    SendMessage купоны = new SendMessage(chatid,aba);
                    // Тут мы добавляем исключения, оно необходимо, чтобы выводились сообщения.
                    try {
                        execute(купоны);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    // Строчкой ниже мы берем строчку "картиночки" и выводим из нее данные по 1 элементу цепочкой. В данном примере будут выводиться только картинки.
                    if (картиночки != null){
                        SendMessage картиночки1 = new SendMessage(chatid,картиночки);


                        try {
                            execute(картиночки1);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                    }
                    // Тут сохраняются данные по циклу. Программа запоминает конечное количество выведенных данных и не дает циклу начинать с первого элемента повторно.
                    i++;
                    j++;
                    c++;
                    k++;
            }}
        }
        // Строчкой ниже мы пишем парсер и команды для вывода купонов Бургур Гинга. Действуем по аналогии из структуры КФС

                    if (text1.equals("/bk")){

                        Document el = null;
                        try {
                            el = Jsoup.connect("https://burgerking-kuponi.ru/").get();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Elements doc = el.getElementsByClass("col-sm-02"); // Элемент каждого лота по отдельности
                        Elements купон = doc.select("p[class=vw-post-box-title]");// название купона
                        Elements картинки1 = doc.select("div[class=vw-post-box vw-post-style-box vw-post-format-standard]");
                        Elements каз = doc.select("img[src$=.jpg]");          // тут мы получаем html код с ссылками на картинки

                        int m = 0;
                        int l =0;

                        while (m<купон.size() && l<20){

                            String купончик = купон.get(m).text();

                            String картиночки = ("https://burgerking-kuponi.ru" + каз.get(l).attr("data-src"));

                            if ( купончик != null){
                                SendMessage купсы = new SendMessage(chatid, купончик);

                                try {
                                    execute(купсы);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            }

                            if ( картиночки != null){
                                SendMessage карты = new SendMessage(chatid, картиночки);

                                try {
                                    execute(карты);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            }
                            m++;
                            l++;
                        }
                    }
        // Кидаем исключение
        try {
            this.execute(text);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
