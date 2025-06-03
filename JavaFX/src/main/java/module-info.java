module pt.ul.fc.di.css.javafxexample {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  requires transitive javafx.graphics;
  requires com.fasterxml.jackson.databind;
  requires java.net.http;
  requires jackson.datatype.jsr310;

  opens pt.ul.fc.di.css.javafxexample to
      javafx.fxml,
      javafx.web;
  opens pt.ul.fc.di.css.javafxexample.controller to
      javafx.fxml;
  opens pt.ul.fc.di.css.javafxexample.controller.utilizadores to
      javafx.fxml;
  opens pt.ul.fc.di.css.javafxexample.controller.equipas to
      javafx.fxml;
  opens pt.ul.fc.di.css.javafxexample.controller.jogos to
      javafx.fxml;
  opens pt.ul.fc.di.css.javafxexample.controller.campeonatos to
      javafx.fxml;

  exports pt.ul.fc.di.css.javafxexample;
  exports pt.ul.fc.di.css.javafxexample.dto.campeonatos;
  exports pt.ul.fc.di.css.javafxexample.dto.jogos;
  exports pt.ul.fc.di.css.javafxexample.dto.equipas;
  exports pt.ul.fc.di.css.javafxexample.dto.utilizadores;
}
