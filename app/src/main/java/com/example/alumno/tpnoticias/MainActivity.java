package com.example.alumno.tpnoticias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback, IListener {
    public static final int NOTICIAS = 1;
    public static final int IMAGEN = 2;
    private static Handler handler;
    private static MyAdapter adapter;
    private static List<Noticia> noticias = new LinkedList<>();
    public static SharedPreferences pref;
    public static LinearLayout layout;


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
    layout = (LinearLayout) findViewById(R.id.checkLayout);

    pref = getSharedPreferences("miConfig", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    String url;
    if ("No existe".equals(pref.getString("allUrl", "No existe"))){
        url = "http://www.telam.com.ar/rss2/ultimasnoticias.xml https://www.perfil.com/rss/ultimo-momento https://www.pagina12.com.ar/rss/portada https://www.clarin.com/rss/lo-ultimo/ https://cronicaglobal.elespanol.com/es/rss/general-001.xml";
        editor.putString("allUrl", url);
        editor.putString("prefUrl", url);
        editor.commit();
    }
    String[] prefUrl = pref.getString("prefUrl", "error").split(" ");
    if (!"error".equals(prefUrl[0])){
      for (String s : prefUrl){
          ConnectionThread thread = new ConnectionThread(handler, s, NOTICIAS);
          thread.start();
      }
    }

  }

    @Override
  public boolean handleMessage(Message msg) {
    if (msg.arg1 == NOTICIAS){
      noticias.addAll((LinkedList) msg.obj);
      Collections.sort(noticias);
      adapter.notifyDataSetChanged();
    }
    else if (msg.arg1 == IMAGEN){
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
        if (item.getItemId() == R.id.opConfig){
            SetupDialog dialog = new SetupDialog();
            dialog.show(getSupportFragmentManager(), "Settings");
            String[] allUrl = pref.getString("allUrl", "error").split(" ");
            for (String s : allUrl){
                CheckBox cb = new CheckBox(this);
                cb.setText(s);
                cb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
             //   layout.addView(cb);
            }
        }
        return super.onOptionsItemSelected(item);
    }
/*
    public static void actualizar(String newUrl){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("prefUrl", newUrl);
        editor.commit();

        String[] prefUrl = pref.getString("prefUrl", "error").split(" ");
        if (!"error".equals(prefUrl)){
            noticias.clear();
            for (String s : prefUrl){
                ConnectionThread thread = new ConnectionThread(handler, s, NOTICIAS);
                thread.start();
            }
        }
    }*/
}
