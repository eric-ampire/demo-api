package org.esisalama.mobile.project.demoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewLogin;
    private TextView textViewName;
    private TextView textViewId;
    private EditText editTextId;
    private Button boutonSoumettre;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setEcouteurEvenement();
    }

    private void setEcouteurEvenement() {
        boutonSoumettre.setOnClickListener(v -> {
            String idText = editTextId.getText().toString();
            if (idText.isEmpty()) {
                Toast.makeText(MainActivity.this, "L'id ne peux pas etre vide", Toast.LENGTH_LONG).show();
            } else {
                int id = Integer.parseInt(idText);
                recupererGithubUser(id);
            }
        });
    }

    private void recupererGithubUser(int id) {
        progressBar.setVisibility(View.VISIBLE);

        // Creation de l'object retrofit
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        // Creation creation du service
        GithubUserService userService = retrofit.create(GithubUserService.class);

        // Soumission de la request
        Call<GithubUser> callback = userService.getUser(id);

        // Ajouter un callback
        callback.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                if (response.isSuccessful() ) {
                    // Affichage des informations
                    GithubUser user = response.body();
                    if (user == null) {
                        Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_LONG).show();
                    } else {
                        textViewLogin.setText("Login : " + user.getLogin());
                        textViewName.setText("Name : " + user.getName());
                        textViewId.setText("Id : + " + user.getId());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initialization() {
        textViewLogin = findViewById(R.id.textViewLogin);
        textViewName = findViewById(R.id.textViewName);
        textViewId = findViewById(R.id.textViewId);
        editTextId = findViewById(R.id.editTextId);
        boutonSoumettre = findViewById(R.id.boutonSubmit);
        progressBar = findViewById(R.id.progress_bar);
    }
}