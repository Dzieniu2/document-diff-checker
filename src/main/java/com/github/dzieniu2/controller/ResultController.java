package com.github.dzieniu2.controller;

import com.github.dzieniu2.vo.SentenceRow;
import com.github.dzieniu2.vo.WordRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.HashMap;

public class ResultController {

    @FXML
    private ComboBox comboBox;

    private TableColumn columnSentence,columnPattern,columnSelected,
            columnPatternBeginLine,columnPatternBeginIndex,
            columnSelectedBeginLine,columnSelectedBeginIndex,
            columnWord,columnWordCount;

    @FXML
    private Label numberLabelSentence,simLabel;

    @FXML
    private TableView resultTableView;

    @FXML
    private Label resultLabel;

    @FXML
    private Button closeButton;

    private int similarSentences;

    private int sentencesCount;

    private ObservableList sentenceMatchResult,wordMatchResult;

    private int simWordsCount;

    private int allWordsCount;

    private HashMap<String,Integer> wordsContained;

    public void initialize() {};

    public void initData(String result) {
        resultLabel.setText(result);
    }

    public void initDataSentence(int number,int sentencesCount,ObservableList result,
                                 int simWordsCount, int allWordsCount, ObservableList result2) {

        columnSentence = new TableColumn();
        columnSentence.setPrefWidth(205);
        columnSentence.setText("Sentence");

        columnPattern = new TableColumn();
        columnPattern.setPrefWidth(160);
        columnPattern.setText("Pattern");

        columnSelected = new TableColumn();
        columnSelected.setPrefWidth(160);
        columnSelected.setText("Selected");

        columnPatternBeginLine = new TableColumn();
        columnPatternBeginLine.setMinWidth(80);
        columnPatternBeginLine.setText("Line");

        columnPatternBeginIndex = new TableColumn();
        columnPatternBeginIndex.setMinWidth(80);
        columnPatternBeginIndex.setText("Index");

        columnSelectedBeginLine = new TableColumn();
        columnSelectedBeginLine.setMinWidth(80);
        columnSelectedBeginLine.setText("Line");

        columnSelectedBeginIndex = new TableColumn();
        columnSelectedBeginIndex.setMinWidth(80);
        columnSelectedBeginIndex.setText("Index");

        columnWord = new TableColumn();
        columnWord.setMinWidth(300);
        columnWord.setText("Word");

        columnWordCount = new TableColumn();
        columnWordCount.setMinWidth(225);
        columnWordCount.setText("Found");

        columnPattern.getColumns().addAll(columnPatternBeginLine,columnPatternBeginIndex);
        columnSelected.getColumns().addAll(columnSelectedBeginLine,columnSelectedBeginIndex);

        resultTableView.getColumns().addAll(columnSentence,columnPattern,columnSelected);

        this.similarSentences = number;
        this.sentencesCount = sentencesCount;
        this.sentenceMatchResult = result;

        this.simWordsCount = simWordsCount;
        this.allWordsCount = allWordsCount;
        this.wordMatchResult = result2;

        comboBox.getItems().addAll("sentence","word");
        comboBox.getSelectionModel().select(0);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue observableValue, String oldValue, String newValue) {
                if(oldValue.toString().matches("word")) loadSentenceView();
                if(oldValue.toString().matches("sentence")) loadWordView();
            }
        });

        columnSentence.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("sentence"));

        columnPatternBeginLine.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("patternBeginLine"));
        columnPatternBeginIndex.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("patternBeginIndex"));
        columnSelectedBeginLine.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("selectedBeginLine"));
        columnSelectedBeginIndex.setCellValueFactory(
                new PropertyValueFactory<SentenceRow, String>("selectedBeginIndex"));

        columnWord.setCellValueFactory(
                new PropertyValueFactory<WordRow, String>("word"));
        columnWordCount.setCellValueFactory(
                new PropertyValueFactory<WordRow, String>("counter"));

        resultTableView.setItems(result);
        simLabel.setText("Sentence similiarity: "+((double) number/sentencesCount)*100 +"%");
        numberLabelSentence.setText("Matching sentences found:"+number);
    }

    public void loadSentenceView(){

        simLabel.setText("Sentence similiarity: "+((double) similarSentences/sentencesCount)*100 +"%");
        numberLabelSentence.setText("Matching sentences found:"+similarSentences);
    }

    public void loadWordView(){

        resultTableView.getColumns().clear();
        resultTableView.getColumns().addAll(columnWord,columnWordCount);

        resultTableView.getItems().clear();
        resultTableView.getItems().addAll(wordMatchResult);

        simLabel.setText("Word similiarity: "+((double) simWordsCount/allWordsCount)*100 +"%");
        numberLabelSentence.setText("Matching words found:"+simWordsCount);
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
