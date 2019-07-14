# AndroidPdfDownloader
#### This project aims to help download pdf and then render it. The rendering pdf is using codes from [googlesamples/android-PdfRendererBasic](https://github.com/googlesamples/android-PdfRendererBasic)


# Usage

*For a working implementation, please have a look at the Sample Project - app*

1. Include the library as local library project.

	```
	allprojects {
	   repositories {
	      jcenter()
	      maven { url "https://jitpack.io" }
	   }
	}
	```

    ``` implementation 'com.github.yusufaw:AndroidPdfDownloader:latest-release' ```
   
    
2. Add SkyPdfDownloader into your AndroidManifest.xml

    ```
    <activity
            android:name="com.skyshi.skypdf.SkyPdfDownloader"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme" />
    ```

3. The AndroidPdfDownloader configuration is created using the builder pattern.

	```java
    SkyPdfDownloader.of(stringUrl, fileName)
                .withDescription("This is a awesome description")
                .start(context)
    ```

#### Apps using AndroidPdfDownloader

- [Passpod](https://play.google.com/store/apps/details?id=com.passpod).
