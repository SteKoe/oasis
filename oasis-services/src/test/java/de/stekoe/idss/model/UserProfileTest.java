package de.stekoe.idss.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;


public class UserProfileTest {

    private UserProfile userProfile;

    @Before
    public void setUp() {
        userProfile = new UserProfile();
    }

    @Test
    public void getFullNameFirstAndSurname() throws Exception {
        userProfile.setFirstname("Hans");
        userProfile.setSurname("Wurst");
        assertThat(userProfile.getFullName(), is(equalTo("Hans Wurst")));
    }

    @Test
    public void getFullNameFirstnameOnly() throws Exception {
        userProfile.setFirstname("Hans");
        assertThat(userProfile.getFullName(), is(equalTo("Hans")));
    }

    @Test
    public void getFullNameSurnameOnly() throws Exception {
        userProfile.setSurname("Wurst");
        assertThat(userProfile.getFullName(), is(equalTo("Wurst")));
    }

    @Test
    public void getFullNameAllNamesNumm() throws Exception {
        assertThat(userProfile.getFullName(), is(equalTo(null)));
    }
}
