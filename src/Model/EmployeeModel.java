package Model;

import java.util.List;

import DAO.EmployeeDAOImpl;
import Utilities.Utils;
import View.EmployeeView;

public class EmployeeModel {
    private EmployeeDAOImpl dao;
    public EmployeeModel(EmployeeDAOImpl dao) {
        this.dao = dao;
    }

    public boolean ajouterEmployee(String nom, String prenom, String salaire, String email, String phone, Role role, Poste poste) {
        double salaireDouble = Utils.parseDouble(salaire);
        if(nom.trim().isEmpty() || prenom.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || salaireDouble == 0) {
            EmployeeView.AjouterFail("Veuillez remplir tous les champs.");
            return false;
        }
        if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            EmployeeView.AjouterFail("Veuillez entrer une adresse email valide.");
            return false;
        }
        if(!phone.matches("^0\\d{9}$")) {
            EmployeeView.AjouterFail("Le numéro de téléphone doit contenir 10 chiffres");
            return false;
        }
        
        if(salaireDouble < 0 ){
            EmployeeView.AjouterFail("Le salaire doit être un nombre positif");
            return false;
        }
        Employee employee = new Employee(0, nom, prenom, salaireDouble, email, phone, role, poste,25);
        return dao.ajouter(employee);
    }

    public List<Employee> afficherEmployee() {
        return dao.afficher();
    }
    public void supprimerEmployee(int id) {
        if(EmployeeView.SupprimerConfirmation()){
            dao.supprimer(id);
        }
        return;
    }
    public Employee findById(int id) {
        return dao.findById(id);
    }
    public void updateEmployee(Employee employee,int id,String nom,String prenom,String email,double salaire,String phone,Role role,Poste poste) {
        if(nom.trim().isEmpty() && prenom.trim().isEmpty() && email.trim().isEmpty() && phone.trim().isEmpty() && salaire == 0 && role == null && poste == null) {
            EmployeeView.ModifierFail("Veuillez remplir au moins un champ.");
            return;
        }            
        if(!nom.trim().isEmpty()) employee.setNom(nom);
        if(!prenom.trim().isEmpty()) employee.setPrenom(prenom);
        if(!email.trim().isEmpty()){
            if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                EmployeeView.ModifierFail("Veuillez entrer une adresse email valide.");
                return;
            }
            employee.setEmail(email);
        }
        if(salaire != 0) {
            if(salaire < 0 ){
                EmployeeView.ModifierFail("Le salaire doit être un nombre positif");
                return;
            }
            employee.setSalaire(salaire);
        };
        if(!phone.isEmpty()){
            if(!phone.matches("^0\\d{9}$")) {
                EmployeeView.ModifierFail("Le numéro de téléphone doit contenir 10 chiffres");
                return;
            }
            employee.setPhone(phone);
        } 
        if(role != null) employee.setRole(role);
        if(poste != null) employee.setPoste(poste);
        dao.modifier(employee,id);
    }
}