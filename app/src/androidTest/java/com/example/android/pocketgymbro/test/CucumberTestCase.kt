package com.example.android.pocketgymbro.test

import cucumber.api.CucumberOptions
import junit.framework.TestCase

/**
 * This file is used to set the cucumber options.
 *
 * It's a way to add options to the test you run
 * in the IDE, is similar to how would if you
 * run cucumber from the command line.
 *
 * Some important options are:
 *  features: sets the location of the feature file(s) to be tested
 *  glue: sets the location of the steps needed for the feature file
 *  tags: runs test governed by the tag logic
 *
 *
 * Some tests with different injections should not be run together.
 * e.g. run feature tests and integration tests separately.
 *
 * TODO: Look into adding tests tags to add appropriate injections so
 * the whole test suite may be run once.
 */

@CucumberOptions(
    features = arrayOf( "db","integration/util", "integration/exercise_search_suggestion"),
//    features = arrayOf("features"),
    glue = arrayOf("com.example.android.pocketgymbro.cucumber.steps")
)
class CucumberTestCase: TestCase()