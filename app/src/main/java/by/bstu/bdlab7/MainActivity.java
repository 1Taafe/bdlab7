package by.bstu.bdlab7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String choosenDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choosenDate = String.valueOf(java.time.LocalDate.now());
        Note.Deserialize(getFilesDir());

        for (Note n: Note.Collection) {
            if(n.Date.equals(choosenDate)){
                EditText noteInput = findViewById(R.id.noteInput);
                noteInput.setText(n.Text);
                Toast.makeText(getApplicationContext(), "Найдена заметка", Toast.LENGTH_LONG).show();
                break;
            }
            else {
                EditText noteInput = findViewById(R.id.noteInput);
                noteInput.setText("");
            }
        }

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            int mYear = year;
            int mMonth = month;
            int mDay = dayOfMonth;
            String selectedDate = "";
            if(dayOfMonth / 10 == 0){
                selectedDate = new StringBuilder().append(mYear)
                        .append("-").append(mMonth+1).append("-").append("0").append(mDay).toString();
            }
            else{
                selectedDate = new StringBuilder().append(mYear)
                        .append("-").append(mMonth+1).append("-").append(mDay).toString();
            }

            choosenDate = selectedDate;

            for (Note n: Note.Collection) {
                if(n.Date.equals(choosenDate)){
                    EditText noteInput = findViewById(R.id.noteInput);
                    noteInput.setText(n.Text);
                    //Toast.makeText(getApplicationContext(), "Найдена заметка", Toast.LENGTH_LONG).show();
                    break;
                }
                else {
                    EditText noteInput = findViewById(R.id.noteInput);
                    noteInput.setText("");
                    }
                }
            }
        });
    }

    public void saveButtonClick(View view) {
        EditText noteInput = findViewById(R.id.noteInput);
        String text = String.valueOf(noteInput.getText());
        if(text.length() < 1){
            Toast.makeText(getApplicationContext(), "Введите заметку!", Toast.LENGTH_LONG).show();
        }
        else{
            for (Note n: Note.Collection) {
                if(n.Date.equals(choosenDate)){
                    Note.Collection.remove(n);
                    Toast.makeText(getApplicationContext(), "Заметка перезаписана!", Toast.LENGTH_LONG).show();
                    break;
                }
            }
            if(Note.Collection.size() < 11){
                Note newNote = new Note(text, choosenDate);

                Toast.makeText(getApplicationContext(), "Заметка создана!", Toast.LENGTH_LONG).show();
                Note.Collection.add(newNote);
                Note.Serialize(getFilesDir());
            }
            else{
                Toast.makeText(getApplicationContext(), "Мы не можем хранить больше 10 заметок!", Toast.LENGTH_LONG).show();
            }

        }


    }

    public void deleteButtonClick(View view) {
        for (Note n: Note.Collection) {
            if(n.Date.equals(choosenDate)){
                Note.Collection.remove(n);
                Note.Serialize(getFilesDir());
                EditText noteInput = findViewById(R.id.noteInput);
                noteInput.setText("");
                Toast.makeText(getApplicationContext(), "Заметка удалена!", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}