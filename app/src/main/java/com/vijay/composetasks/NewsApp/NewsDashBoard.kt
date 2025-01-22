package com.vijay.composetasks.NewsApp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.vijay.composetasks.NewsApp.model.Article
import com.vijay.composetasks.TodoList2.Data
import com.vijay.composetasks.TodoList2.TaskData

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsDashBoard (viewModel: NewsViewModel) {
    val article by viewModel.articles.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "News App") })
        }
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        article?.let {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                content = {
                itemsIndexed(it) { index: Int, item: Article ->
                    NewsList(article = item)
                }
            }
            )
        }
    }
}

@Composable
fun NewsList(article: Article){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                 //   navController.navigate()
                }
        ) {
                Text(text = article.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                article.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(8.dp))
                article.urlToImage?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }

}
