package com.example.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.retrofit.data.ProductsRepositoryImpl
import com.example.retrofit.data.model.Product
import com.example.retrofit.presentation.ProductsViewModel
import com.example.retrofit.ui.theme.RetrofitTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<ProductsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory{
            override fun <T: ViewModel> create(modelClass: Class<T>): T{
                return ProductsViewModel(ProductsRepositoryImpl(RetrofitInstance.api))
                        as T
            }
        }
    }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val productList = viewModel.products.collectAsState().value
                    val context = LocalContext.current

                    LaunchedEffect(key1 = viewModel.showErrorToastChannel){
                        viewModel.showErrorToastChannel.collectLatest { show ->
                            if (show){
                               Toast.makeText(
                                   context, "Error",Toast.LENGTH_SHORT)
                                   .show()
                            }
                        }
                    }
                    if (productList.isEmpty()){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator()
                        }
                    } else{
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(16.dp)
                        ){
                            items(productList.size){index ->
                                Product(productList[index])
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Color.LightGray)
                                        .fillMaxWidth()
                                        .height(1.dp),
                                )
                            }

                        }

                    }

                }
            }
        }
    }
}

@Composable
fun Product(product: Product){
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(product.thumbnail)
            .size(Size.ORIGINAL).build()
    ).state

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(300.dp)
            .fillMaxWidth()
    ){

        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = imageState.painter,
                contentDescription = product.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "${product.title} -- Price: ${product.price}$",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = product.description,
            fontSize = 13.sp,
        )
    }
}



















