package com.microsoft.projectoxford.face.samples.ui;

class Patient {

    String email,  patientName, patientAge, patientOccupation, patientCaretaker_name, patientCaretaker_contact, patientEmergency_contact;

    public Patient(String email, String patientName,String patientAge, String patientOccupation, String patientCaretaker_name, String patientCaretaker_contact, String patientEmergency_contact){
        this.email = email;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientOccupation = patientOccupation;
        this.patientCaretaker_name = patientCaretaker_name;
        this.patientCaretaker_contact = patientCaretaker_contact;
        this.patientEmergency_contact = patientEmergency_contact;
    }

    public Patient() {}


    public String getEmail() {
        return email;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public String getPatientOccupation() {
        return patientOccupation;
    }

    public String getPatientCaretaker_name() {
        return patientCaretaker_name;
    }

    public String getPatientCaretaker_contact() {
        return patientCaretaker_contact;
    }

    public String getPatientEmergency_contact() {
        return patientEmergency_contact;
    }
}