package com.superspork.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void indexAction_works() {

        String body = this.restTemplate.getForObject("/", String.class);
        System.out.println(body);

        assertThat(body).contains("Harry Potter");
        assertThat(body).contains("1 times");

	}

    @Test
    public void nameAction_works() {

	    String name = "gamma";

        String body = this.restTemplate.getForObject("/name/" + name, String.class);
        System.out.println(body);

        assertThat(body).contains(name);
        assertThat(body).contains("1 times");

    }

    @Test
    public void nameAction_worksWithMultipleNames() {

        String name1 = "alpha";
        String name2 = "beta";

        String body1 = null, body2 = null;

        for (int i=0; i<3; i++)
            body1 = this.restTemplate.getForObject("/name/" + name1, String.class);

        for (int i=0; i<10; i++)
            body2 = this.restTemplate.getForObject("/name/" + name2, String.class);

        assertThat(body1).contains(name1);
        assertThat(body2).contains(name2);

        assertThat(body1).contains("3 times");
        assertThat(body2).contains("10 times");

    }

}
