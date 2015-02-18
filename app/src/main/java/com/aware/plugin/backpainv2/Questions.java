package com.aware.plugin.backpainv2;

import com.aware.ESM;

public class Questions {

    private static Questions qs;
    private static final String[] questions_en = new String[12];
    private static final String[] questions = new String[12];

    private Questions() {

        questions_en[0] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_TEXT + "," +
                "'esm_title': 'Register'," +
                "'esm_instructions': 'Enter your uid below'," +
                "'esm_submit': 'Register'," +
                "'esm_expiration_threashold': 300," + //the user has 2 minutes to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[1] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_QUICK_ANSWERS + "," +
                "'esm_title': 'Back Pain Questionnaire (1 of 11)'," +
                "'esm_instructions': 'Did you have low back pain during the past 7 days?'," +
                "'esm_quick_answers': ['No','Yes']," +
                "'esm_expiration_threashold': 300," + //the user has 20 minutes to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[2] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Back Pain Questionnaire (2 of 11)'," +
                "'esm_instructions': 'How intense on average your pain was?'," +
                "'esm_radios':['0: No Pain', '1', '2', '3', '4', '5: Somewhat painful', '6', '7', '8', '9', '10: Worst Imaginable Pain']," +
                "'esm_submit': 'Next'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[3] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Back Pain Questionnaire (3 of 11)'," +
                "'esm_instructions': 'In the past 7 days, how much did pain interfere with your day-to-day activities?'," +
                "'esm_radios':['0: Not at all', '1', '2', '3', '4', '5: Somewhat interfered', '6', '7', '8', '9', '10: Very much']," +
                "'esm_submit': 'Next'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[4] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Back Pain Questionnaire (4 of 11)'," +
                "'esm_instructions': 'In the past 7 days, how much did pain interfere with work around the home?'," +
                "'esm_radios':['0: Not at all', '1', '2', '3', '4', '5: Somewhat interfered', '6', '7', '8', '9', '10: Very much']," +
                "'esm_submit': 'Next'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[5] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Back Pain Questionnaire (5 of 11)'," +
                "'esm_instructions': 'In the past 7 days, how much did pain interfere with your ability to participate in social activities?'," +
                "'esm_radios':['0: Not at all', '1', '2', '3', '4', '5: Somewhat interfered', '6', '7', '8', '9', '10: Very much']," +
                "'esm_submit': 'Next'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[6] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Back Pain Questionnaire (6 of 11)'," +
                "'esm_instructions': 'In the past 7 days, how much did pain interfere with your household chores?'," +
                "'esm_radios':['0: Not at all', '1', '2', '3', '4', '5: Somewhat interfered', '6', '7', '8', '9', '10: Very much']," +
                "'esm_submit': 'Next'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[7] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_QUICK_ANSWERS + "," +
                "'esm_title': 'Back Pain Questionnaire (7 of 11)'," +
                "'esm_instructions': 'Did you have radiating pain from low back to your leg during the past 7 days?'," +
                "'esm_quick_answers': ['No','Yes']," +
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[8] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Back Pain Questionnaire (8 of 11)'," +
                "'esm_instructions': 'How intense on average your radiating pain was?'," +
                "'esm_radios':['0: No Pain', '1', '2', '3', '4', '5: Somewhat painful', '6', '7', '8', '9', '10: Worst Imaginable Pain']," +
                "'esm_submit': 'Next'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[9] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Back Pain Questionnaire (9 of 11)'," +
                "'esm_instructions': 'Have you been working or on sick leave during the past week?'," +
                "'esm_radios':['I have been working normally', 'I have been on full sick leave', 'I have been on part-time sick leave', 'I have neither been working nor on sick leave']," +
                "'esm_submit': 'Next'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[10] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_QUICK_ANSWERS + "," +
                "'esm_title': 'Back Pain Questionnaire (10 of 11)'," +
                "'esm_instructions': 'Have work modifications been done at your work because of your back or leg pain?'," +
                "'esm_quick_answers': ['No','Yes']," +
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions_en[11] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_TEXT + "," +
                "'esm_title': 'Back Pain Questionnaire (11 of 11)'," +
                "'esm_instructions': 'Describe the modifications. You can write as much as you wish'," +
                "'esm_submit': 'Submit'," +
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";



        questions[0] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_TEXT + "," +
                "'esm_title': 'Rekisteröinti'," +
                "'esm_instructions': 'Kirjoita käyttäjätunnuksesi kenttään alapuolelle'," +
                "'esm_submit': 'Rekisteröidy'," +
                "'esm_expiration_threashold': 300," + //the user has 20 minutes to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[1] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_QUICK_ANSWERS + "," +
                "'esm_title': 'Selkäkipukysely (1 /11)'," +
                "'esm_instructions': 'Onko sinulla ollut alaselkäkipua viimeisen viikon aikana?'," +
                "'esm_quick_answers': ['Ei','Kyllä']," +
                "'esm_expiration_threashold': 300," + //the user has 20 minutes to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[2] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Selkäkipukysely (2/11)'," +
                "'esm_instructions': 'Miten voimakas selkäkipusi on ollut keskimäärin viimeisen viikon aikana? Rastita oikea vaihtoehto.'," +
                "'esm_radios':['0: Ei lainkaan kipua', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10: Sietämätön kipu']," +
                "'esm_submit': 'Seuraava'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[3] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Selkäkipukysely (3/11)'," +
                "'esm_instructions': 'Kuinka paljon selkäkipusi haittasi jokapäiväistä elämääsi viimeisen viikon aikana? Rastita oikea vaihtoehto.'," +
                "'esm_radios':['0: Ei lainkaan', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10: Todella paljon']," +
                "'esm_submit': 'Seuraava'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[4] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Selkäkipukysely (4/11)'," +
                "'esm_instructions': 'Kuinka paljon selkäkipus haittas työntekoa kodin ulkopuolella viimeisen viikon aikana? Rastita oikea vaihtoehto.'," +
                "'esm_radios':['0: Ei lainkaan', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10: Todella paljon']," +
                "'esm_submit': 'Seuraava'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[5] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Selkäkipukysely (5/11)'," +
                "'esm_instructions': 'Kuinka paljon selkäkipusi haittasi sosiaalista kanssakäyntiä viimeisen viikon aikana? Rastita oikea vaihtoehto.'," +
                "'esm_radios':['0: Ei lainkaan', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10: Todella paljon']," +
                "'esm_submit': 'Seuraava'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[6] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Selkäkipukysely (6/11)'," +
                "'esm_instructions': 'Kuinka paljon selkäkipusi haittasi kotitöiden tekemistä viimeisen viikon aikana? Rastita oikea vaihtoehto.'," +
                "'esm_radios':['0: Ei lainkaan', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10: Todella paljon']," +
                "'esm_submit': 'Seuraava'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[7] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_QUICK_ANSWERS + "," +
                "'esm_title': 'Selkäkipukysely (7/11)'," +
                "'esm_instructions': 'Onko sinulla ollut alaselästä jalkaan säteilevää kipua viimeisen viikon aikana?'," +
                "'esm_quick_answers': ['Ei','Kyllä']," +
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[8] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Selkäkipukysely (8/11)'," +
                "'esm_instructions': 'Kuinka voimakas alaselästä jalkaan säteilevä kipusi on ollut keskimäärin viimeisen viikon aikana? Rastita oikea vaihtoehto.'," +
                "'esm_radios':['0: Ei lainkaan kipua', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10: Sietämätön kipu']," +
                "'esm_submit': 'Seuraava'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[9] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
                "'esm_title': 'Selkäkipukysely (9/11)'," +
                "'esm_instructions': 'Minkälainen työkykysi on ollut viimeisen viikon aikana?'," +
                "'esm_radios':['Normaalisti töissä', 'Kokonaan sairauslomalla', 'Osa-aikaisesti sairauslomalla', 'Ei töissä eikä sairauslomalla']," +
                "'esm_submit': 'Seuraava'," + //submit button label
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[10] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_QUICK_ANSWERS + "," +
                "'esm_title': 'Selkäkipukysely (10/11)'," +
                "'esm_instructions': 'Onko työhösi tehty muutoksia viimeisen viikon aikana selkä- tai jalkakipujesi takia?'," +
                "'esm_quick_answers': ['Ei','Kyllä']," +
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
        questions[11] = "{'esm':{" +
                "'esm_type':" + ESM.TYPE_ESM_TEXT + "," +
                "'esm_title': 'Selkäkipukysely (11/11)'," +
                "'esm_instructions': 'Kuvaile tehdyt muutokset. Voit kirjoittaa niin paljon kuin haluat.'," +
                "'esm_submit': 'Lähetä'," +
                "'esm_expiration_threashold': 300," + //the user has 60 seconds to respond. Set to 0 to disable
                "'esm_trigger': 'com.aware.plugin.backpainv2'" +
                "}}";
    }



    public static Questions getInstance() {
        if (qs == null) {
            qs = new Questions();
        }
        return qs;
    }

    public String getQuestion(int qn) {
        return this.questions[qn];
    }

}