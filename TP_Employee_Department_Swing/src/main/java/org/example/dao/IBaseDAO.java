package org.example.dao;

import org.example.model.Employee;

import java.util.List;

public interface IBaseDAO <T>{

    List<T> getAll();           // Récupérer tous les objets
    T findById(int id);        // Trouver un objet par son ID
    int save(T t);             // Enregistrer un nouvel objet (ajouter)
    int update(T t);           // Mettre à jour un objet existant
    boolean delete(int id);    // Supprimer un objet par son ID




}
