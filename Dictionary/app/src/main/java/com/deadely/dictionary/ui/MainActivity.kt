package com.deadely.dictionary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadely.dictionary.App
import com.deadely.dictionary.R
import com.deadely.dictionary.dataclass.Word
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val words = App.instance.db.wordDao()
    private var dataList = listOf<Word>()
    private lateinit var wordsAdapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addDataToDatabase()
        initView()
    }

    private fun addDataToDatabase() {
        words.addAllWord(
            listOf(
                Word(
                    0,
                    "ПОНИМАНИЕ",
                    "- способность осмыслять, постигать содержание, смысл, значение"
                ),
                Word(1, "ВЕЧНОСТЬ", "- очень долгое время, бесконечность"),
                Word(2, "ЦЕННОСТЬ", "- важность, значение"),
                Word(3, "ЦЕЛОМУДРИЕ", "- строгая нравственность, чистота"),
                Word(4, "БЫТИЕ", "- жизнь, существование"),
                Word(5, "ЗАБВЕНИЕ", "- утрата памяти о чём-нибудь"),
                Word(6, "ВЕЖЛИВОСТЬ", "- соблюдение правил приличия, общая склонность к этому"),
                Word(
                    7,
                    "УТОПИЯ",
                    "- изображение идеального обществ. строя, лишённое науч. обоснования"
                ),
                Word(
                    8,
                    "АНТИУТОПИЯ",
                    "- в художественной литературе - проекция в воображаемое будущее пессимистических представлений о социальном процессе"
                ),
                Word(
                    9,
                    "ТОСКА",
                    "- слово, выражающее эмоциональное (в том числе и одобрительное) отношение говорящего к чему-либо"
                )
            )
        )
    }

    private fun initView() {
        dataList = words.getAllWords()
        wordsAdapter = WordsAdapter(dataList)
        rvWords.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordsAdapter
        }
    }
}
