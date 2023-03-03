# IRIDE2 per FIRMIP - Firme di iniziativa popolare

https://intranet.csi.it/web/come-fare-per/come-funziona-iride2/
http://kbt.csi.it/iride/item/353-parametri-ambienti-iride

## Configurazione
Application: FIRMIP

1 Attore: FUNZ_DIR_SETT - Funzionario di Direzione, Settore

3 UC:
GESTIONE_INIZIATIVE	Gestione Iniziative popolare
GESTIONE_FIRME	Gerstione firme
RICERCA_FIRME	Ricerca firme

1 Ruolo: GESTORE_IP@REG_PMN_CRP

Nota: Verra controllato solo la presenza del ruolo GESTORE_IP@REG_PMN_CRP all'identita IRIDE durante la login.

# TEST
PA/PD Cooperazione CSI T3:
t3://tst-exp01wls1.csi.it,tst-exp02wls2.csi.it:7111

Bridge SOAP CSI IRIDE2-PEP:
http://tst-wfexp-vip01.csi.it/pabr_policy/servlet/rpcrouter
https://tst-wfexp-vip01.csi.it/pabr_policy/servlet/rpcrouter

Bridge SOAP CSI IRIDE2-PEP (Fonte: https://intranet.csi.it/web/come-fare-per/come-funziona-iride2/)
http://tst-appweb.reteunitaria.piemonte.it/pabr_policy/servlet/rpcrouter
https://tst-appweb.reteunitaria.piemonte.it:1450/pabr_policy/servlet/rpcrouter

Web Services IRIDE2-PEP:
http://tst-wfexp-vip01.csi.it/pep_wsfad_policy/services/PolicyEnforcerHelper?wsdl 
https://tst-wfexp-vip01.csi.it/pep_wsfad_policy/services/PolicyEnforcerHelper?wsdl 
http://tst-wfexp-vip01.csi.it/pep_wsfad_policy/services/PolicyEnforcerBase?wsdl 
https://tst-wfexp-vip01.csi.it/pep_wsfad_policy/services/PolicyEnforcerBase?wsd


# PARAMETRI AMBIENTE NMSF

IRIDE2
PEP :
http://applogic-nmsf2e.csi.it/pep_wsfad_nmsf_policy/services/PolicyEnforcerBase?WSDL
https://applogic-nmsf2e.csi.it/pep_wsfad_nmsf_policy/services/PolicyEnforcerBase?WSDL

# PROD
PA/PD Cooperazione CSI T3:
t3://irideprod1wls1.csi.it,irideprod1wls2.csi.it,irideprod2wls1.csi.it,irideprod2wls2.csi.it:7103

Bridge SOAP CSI IRIDE2-PEP:
http://applogic.csi.it/pabr_policy/servlet/rpcrouter
https://applogic.csi.it/pabr_policy/servlet/rpcrouter

Web Services IRIDE2-PEP:
http://applogic.csi.it/pep_wsfad_policy/services/PolicyEnforcerHelper?wsdl 
https://applogic.csi.it/pep_wsfad_policy/services/PolicyEnforcerHelper?wsdl 
http://applogic.csi.it/pep_wsfad_policy/services/PolicyEnforcerBase?wsdl 
https://applogic.csi.it/pep_wsfad_policy/services/PolicyEnforcerBase?wsdl 

2 Utenti:
DMPNUO01A01L219C	
DMPDUE01A02L219N