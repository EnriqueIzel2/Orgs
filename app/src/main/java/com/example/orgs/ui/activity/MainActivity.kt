package com.example.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.model.Produto
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val nome = findViewById<TextView>(R.id.nome)
//        val descricao = findViewById<TextView>(R.id.descricao)
//        val valor = findViewById<TextView>(R.id.valor)
//
//        nome.text = "Cesta de frutas"
//        descricao.text = "Manga,uva, jaca"
//        valor.text = "19,99"

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = ListaProdutosAdapter(
            context = this, produtos = listOf(
                Produto(nome = "teste", descricao = "Teste task", valor = BigDecimal("19.99")),
                Produto(nome = "teste2", descricao = "Teste task2", valor = BigDecimal("21.99")),
            )
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}