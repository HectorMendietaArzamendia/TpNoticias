package com.example.alumno.tpnoticias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback, IListener {
    public static final int NOTICIAS = 1;
    public static final int IMAGEN = 2;
    private Handler handler;
    private MyAdapter adapter;
    private List<Noticia> noticias = new LinkedList<>();
    public static SharedPreferences pref;
    public static View layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(getResources().getText(R.string.title));

        handler = new Handler(this);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rvMain);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = new MyAdapter(this, noticias, handler);
        rv.setAdapter(adapter);

        pref = getSharedPreferences("miConfig", Context.MODE_PRIVATE);

        Resources r = getResources();
        String[] allUrl = new String[5];
        allUrl[0] = r.getText(R.string.telam).toString();
        allUrl[1] = r.getText(R.string.perfil).toString();
        allUrl[2] = r.getText(R.string.pagina12).toString();
        allUrl[3] = r.getText(R.string.clarin).toString();
        allUrl[4] = r.getText(R.string.cronica).toString();

        for (String url : allUrl) {
            if (pref.getBoolean(url, true)) {
                ConnectionThread thread = new ConnectionThread(handler, url, NOTICIAS);
                thread.start();
            }
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.arg1 == NOTICIAS) {
            noticias.addAll((LinkedList) msg.obj);
            Collections.sort(noticias);
            adapter.notifyDataSetChanged();
        } else if (msg.arg1 == IMAGEN) {
            int position = msg.arg2;
            Noticia n = noticias.get(position);
            n.setImagen((byte[]) msg.obj);
            adapter.notifyItemChanged(position);
        }
        return false;
    }

    @Override
    public void mostrarWeb(int position) {
        Noticia n = noticias.get(position);
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("url", n.getLink());
        startActivity(i);
    }

    @Override
    public void modificarLista(List<Noticia> news) {
        noticias.clear();
        noticias.addAll(news);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuListener listener = new MenuListener(this, noticias);
        MenuItem item = menu.findItem(R.id.opBuscar);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(listener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opConfig) {
            SetupDialog dialog = new SetupDialog();
            dialog.setMain(this);
            dialog.show(getSupportFragmentManager(), "Settings");
        }
        return super.onOptionsItemSelected(item);
    }

    public void actualizarVista() {
        noticias.clear();
        for (View v : layout.getTouchables()) {
            CheckBox cb = (CheckBox) v;
            String url = cb.getText().toString();
            if (pref.getBoolean(url, true)) {
                ConnectionThread thread = new ConnectionThread(handler, url, NOTICIAS);
                thread.start();
            }
        }
    }
}
