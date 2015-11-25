package model;

import java.io.PrintWriter;

import com.google.common.collect.ImmutableMultimap;
import io.dropwizard.servlets.tasks.Task;

/**
 * Created by timp on 12/11/2015.
 */
public class TestTask extends Task  {

    public TestTask() {
        super("TestTask");
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
        System.out.println("============== Test task run");
    }
}
