package com.example.alumno.tpnoticias;

import android.content.Intent;
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback, IListener {
    public static final int NOTICIAS = 1;
    public static final int IMAGEN = 2;
    private MyAdapter adapter;
    private List<Noticia> noticias = new LinkedList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar ab = getSupportActionBar();
    ab.setTitle(getResources().getText(R.string.title));

    /*  https://www.telam.com.ar/rss2/ultimasnoticias.xml
        https://www.pagina12.com.ar/rss/portada
        https://www.clarin.com/rss/lo-ultimo/
        http://contenidos.lanacion.com.ar/herramientas/rss-origen=2 */

    Handler handler = new Handler(this);
    RecyclerView rv = (RecyclerView) findViewById(R.id.rvMain);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    rv.setLayoutManager(layoutManager);
    adapter = new MyAdapter(this, noticias, handler);
    rv.setAdapter(adapter);
    ConnectionThread thread = new ConnectionThread(handler, "https://www.pagina12.com.ar/rss/portada", NOTICIAS);
    thread.start();
    ConnectionThread thread2 = new ConnectionThread(handler, "https://www.clarin.com/rss/lo-ultimo/", NOTICIAS);
    thread2.start();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuListener listener = new MenuListener();
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
        }
        return super.onOptionsItemSelected(item);
    }
}
