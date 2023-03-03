/*
* SPDX-FileCopyrightText: (C) Copyright 2023 Consiglio Regionale Piemonte
*
* SPDX-License-Identifier: EUPL-1.2 */

package it.csi.firmip.firmipbo.dto;

import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;



public class UserInfo {
		
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [implicit-camel-case] 
	private String codFisc = null;
	private String cognome = null;
	private String nome = null;
	private List<String> ruoli = null;

  public UserInfo() {
	  super();
  }
  public UserInfo(String codFisc) {
	  this.codFisc = codFisc;
  }
  public UserInfo(String codFisc, String cognome, String nome) {
	  this.codFisc = codFisc;
	  this.cognome = cognome;
	  this.nome = nome;
  }
  

  /**
   * codice fiscale dell'utente
   **/
  
  @ApiModelProperty(value = "codice fiscale dell'utente")

  // nome originario nello yaml: codFisc 
  public String getCodFisc() {
    return codFisc;
  }
  public void setCodFisc(String codFisc) {
    this.codFisc = codFisc;
  }
  
  /**
   * cognome dell'utente
   **/
  
  @ApiModelProperty(value = "cognome dell'utente")

  // nome originario nello yaml: cognome 
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   * nome dell'utente
   **/
  
  @ApiModelProperty(value = "nome dell'utente")

  // nome originario nello yaml: nome 
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * codici dei ruoli dell'utente
   **/
  
  @ApiModelProperty(value = "list degli ruoli dell'utente")

  // nome originario nello yaml: ruoli 
  public List<String> getRuoli() {
    return ruoli;
  }
  public void setRuoli(List<String> ruoli) {
    this.ruoli = ruoli;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfo userInfo = (UserInfo) o;
    return Objects.equals(codFisc, userInfo.codFisc) &&
        Objects.equals(cognome, userInfo.cognome) &&
        Objects.equals(nome, userInfo.nome) &&
        Objects.equals(ruoli, userInfo.ruoli);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codFisc, cognome, nome, ruoli);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfo {\n");
    sb.append("    codFisc: ").append(toIndentedString(codFisc)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    ruoli: ").append(toIndentedString(ruoli)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

