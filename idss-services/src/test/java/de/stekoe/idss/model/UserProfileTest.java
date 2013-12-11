package de.stekoe.idss.model;

import org.hamcrest.core.Is;
import org.joda.time.DateTime;
import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.Assert.assertThat;

public class UserProfileTest {

    private Calendar checkdate;

    @Test
    public void beforeBirthday() {
        checkdate = Calendar.getInstance();
        checkdate.set(2013, 3, 4);

        Calendar birthdate = Calendar.getInstance();
        birthdate.set(1987, 3, 5);

        TestUserProfile profile = new TestUserProfile();
        profile.setBirthdate(new Date(birthdate.getTimeInMillis()));

        assertThat(profile.getAge(), Is.is(25));
    }

    @Test
    public void afterBirthday() {
        checkdate = Calendar.getInstance();
        checkdate.set(2013, 7, 20);

        Calendar birthdate = Calendar.getInstance();
        birthdate.set(1987, 3, 5);

        TestUserProfile profile = new TestUserProfile();
        profile.setBirthdate(new Date(birthdate.getTimeInMillis()));

        assertThat(profile.getAge(), Is.is(26));
    }

    private class TestUserProfile extends UserProfile {
        @Override
        DateTime getCurrentDate() {
            return new DateTime(checkdate.get(Calendar.YEAR),
                    checkdate.get(Calendar.MONTH),
                    checkdate.get(Calendar.DAY_OF_MONTH), 0, 0);
        }
    }
}
