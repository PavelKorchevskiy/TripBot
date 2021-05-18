package com.telegram.bot;

import com.telegram.model.City;
import com.telegram.repository.CityRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

  private CityRepository repository;

  @Value("${bot.name}")
  private String name;
  @Value("${bot.token}")
  private String token;

  @Override
  public String getBotUsername() {
    return name;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @Override
  public void onUpdateReceived(Update update) {
    String userMessage = update.getMessage().getText();
    SendMessage message = new SendMessage();
    message.setChatId(String.valueOf(update.getMessage().getChatId()));
    if (userMessage.equals("/help")) {
      message.setText(getHelpMessage());
    } else {
      Optional<City> cityOptional = repository.findByName(userMessage);
      if (cityOptional.isPresent()) {
        message.setText(cityOptional.get().getInformation());
      } else {
        message.setText("Информация по данному городу отсутствует. "
            + "Если необходима помощь отправте /help");
      }
    }
    try {
      execute(message);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private String getHelpMessage() {
    StringBuilder builder = new StringBuilder("Введите название города,"
        + " информацию о котором хотите узнать. Список всех доступных городов: ");
    repository.findAll().forEach(city -> builder.append(city.getName()).append(", "));
    return builder.substring(0, builder.lastIndexOf(","));
  }

  @Autowired
  public void setRepository(CityRepository repository) {
    this.repository = repository;
  }
}
