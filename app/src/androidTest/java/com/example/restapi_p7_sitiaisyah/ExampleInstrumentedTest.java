package com.example.restapi_p7_sitiaisyah;

import com.example.restapi_p7_sitiaisyah.R;
import com.example.restapi_p7_sitiaisyah.LoginActivity;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testRegisterThenLoginSuccess() throws InterruptedException {
        String testEmail = "sitiais" + System.currentTimeMillis() + "@gmail.com";
        String testPassword = "123456";

        // code yg mengarahkan ke halaman Register
        onView(withId(R.id.textRegister)).perform(click());

        // kode yg akan mengisi Email dan Password di halaman Register
        onView(withId(R.id.editEmail)).perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // kode yg akan mengklik tombol Register
        onView(withId(R.id.buttonRegister)).perform(click());

        // waktu tunggu untuk kembali ke halaman Login
        Thread.sleep(3000);

        // kode yg mengisi pada halaman login
        onView(withId(R.id.editEmail)).perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.editPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // kode yg akan mengkilik tombol Login
        onView(withId(R.id.buttonLogin)).perform(click());

        // kode yg memberikan teks bahwa verifikasi berhasil masuk ke halaman utama
        Thread.sleep(3000);
        onView(withText("Selamat Datang")).check(matches(isDisplayed()));

        // kode yg akna mengarahkan pengguna ke halaman navigasi Konten
        onView(withId(R.id.navigation_konten)).perform(click());
        Thread.sleep(1000);

        // kode yg akna mengarahkan pengguna ke halaman navigasi
        // Pengingat (disini nama idnya belum saya ganti)
        onView(withId(R.id.navigation_favorite)).perform(click());
        Thread.sleep(1000);

        // kode yg akna mengarahkan pengguna ke halaman navigasi Setting
        onView(withId(R.id.navigation_setting)).perform(click());
        Thread.sleep(1000);
    }
}
