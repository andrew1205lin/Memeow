package com.example.memeow.feature_main.data.repository

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import com.example.memeow.R
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeMemeRepository (contentResolver: ContentResolver): MemeRepository {
    var uriList: MutableList<Uri> = mutableListOf()

    init {
        val bucket_name = "Download"
        Log.i("GETIMG","getImages()")
        val projection = arrayOf<String>(MediaStore.Images.Media._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID
        )
        //val selection = null
        //val selectionArgs = null
        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(bucket_name)
        val sortOrder ="${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            Log.i("GETIMG","has cursor")
            while (cursor.moveToNext()) {
                Log.i("GETIMG","In cursor")
                val id = cursor.getLong(0)


                val imageUri = ContentUris
                    .withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        cursor.getInt(0) // id
                            .toLong()
                    )

                val bucketID = cursor.getInt(3)

                val bucketName = cursor.getString(2)
                Log.i("GETIMG","bucket id : $bucketID ,bucket name: $bucketName ,uri: $imageUri")
                uriList.add(imageUri)
                // Use an ID column from the projection to get
                // a URI representing the media item itself.
            }
        }
        Log.i("GETIMG","uriList.size=${uriList.size}")
    }
    private val memes = mutableListOf<Meme>(
        Meme("cat_1", uriList[0], listOf("cat","1")),
        Meme("cat_2", uriList[1], listOf("cat","2")),
        Meme("cat_3", uriList[2], listOf("cat","3"))
    )
    /*need to be connected to database later*/

    override fun getMemes(): Flow<List<Meme>> {
        return flow { emit(memes) }
    }

    override suspend fun insertMeme(meme: Meme){
        memes.add(meme)
    }

    override suspend fun removeMeme(meme: Meme){
        memes.remove(meme)
    }


}


