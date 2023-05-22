package org.example.animations;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.w3c.dom.Node;

public class Shake {
    private TranslateTransition tt;
    public Shake (Node node){ // Node - объект, который мы передаем в конструктор.
        tt = new TranslateTransition(Duration.millis(70), (javafx.scene.Node) node); // Как долго будет идти анимация
        tt.setFromX(0f); // отступ по "X"
        tt.setByX(10f); // на сколько передвинется он по "X"
        tt.setCycleCount(3); // Сколько раз будет играть анимация
        tt.setAutoReverse(true); // Отвечает за то, чтобы анимация возвращалась на исходную позицию.
    }

    public void play(){
        tt.playFromStart(); // запускает анимацию
    }

}
