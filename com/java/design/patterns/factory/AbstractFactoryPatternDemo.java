package com.java.design.patterns.factory;

// Step 1: Product interfaces
interface Button {
    void render();
}

interface TextField {
    void display();
}

// Step 2: Concrete Products - India
class HindiButton implements Button {
    public void render() {
        System.out.println("Hindi Button Rendered");
    }
}

class HindiTextField implements TextField {
    public void display() {
        System.out.println("Hindi TextField Displayed");
    }
}

// Step 3: Concrete Products - US
class EnglishButton implements Button {
    public void render() {
        System.out.println("English Button Rendered");
    }
}

class EnglishTextField implements TextField {
    public void display() {
        System.out.println("English TextField Displayed");
    }
}

// Step 4: Abstract Factory
interface UIFactory {
    Button createButton();
    TextField createTextField();
}

// Step 5: Concrete Factories
class IndianUIFactory implements UIFactory {
    public Button createButton() {
        return new HindiButton();
    }

    public TextField createTextField() {
        return new HindiTextField();
    }
}

class USUIFactory implements UIFactory {
    public Button createButton() {
        return new EnglishButton();
    }

    public TextField createTextField() {
        return new EnglishTextField();
    }
}

// Step 6: Main class
public class AbstractFactoryPatternDemo {
    public static void main(String[] args) {
        // Change the factory here to switch regions
        UIFactory uiFactory = new IndianUIFactory();

        Button button = uiFactory.createButton();
        TextField textField = uiFactory.createTextField();

        button.render();       // Output: Hindi Button Rendered
        textField.display();   // Output: Hindi TextField Displayed

        System.out.println("---- Switching to US UI ----");

        UIFactory usFactory = new USUIFactory();
        Button usButton = usFactory.createButton();
        TextField usField = usFactory.createTextField();

        usButton.render();     // Output: English Button Rendered
        usField.display();     // Output: English TextField Displayed
    }
}
