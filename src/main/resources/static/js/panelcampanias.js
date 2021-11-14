
const app = Vue.createApp({
    
    data() {
        return {   
            campanias:[],         
            referentes:[],                    
            campaniasReferentes:[],  
            nombreCampania:"",
            descripcionCampania:"", 
            optionTipo:"",
            campaniaTipo:"",
            campaniaSeleccionada:"",
            asuntoMail:"",
            cuerpoMail:"",
            archivoValidado:'no',
            previsualizacion:'no',
            referentesSeleccionadosArr:[],
            file: '',
            fileCampania: '',
            aFiltrarReferente: '',            
            tipoIngresoReferentes:'',
            mailReferente:'',
            nombreReferente:'',
            nombreRefeenteEncontrado:'',            
            filevalidado:'',
            bandera:'',
            campaniaReferenteFiltroCampaniaSeleccionada:'',
            campaniaReferenteFiltroPorReferente:'',
            
        }
        },

    created() {
        axios.get("/api/campanias")
        .then(res => {
            this.campanias = res.data                     
        }),
        axios.get("/api/referentes")
        .then(res => {
            this.referentes = res.data
                    
        })
        /* axios.get("/api/campaniaReferente")
        .then(res => {
            this.campaniasReferentes = res.data  
            console.log(this.campaniasReferentes)
        })     */ 
    },
    methods: {                      
        agregarCampania(){  
            let formData = new FormData();            
            formData.append('fileCampania', this.fileCampania); 
            formData.append('nombreCampania', this.nombreCampania);   
            formData.append('descripcionCampania', this.descripcionCampania);                          
            Swal.fire({
                title: '¿Desea crear la campaña?',
                text: "",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',  
                cancelButtonText: "No!",              
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/crearCampania',                  
                   formData,                                
                   {headers:{}
                  })
                    .then(res => Swal.fire(
                   'Campaña creada con éxito!',
                    '',
                    'success',))
                  .then(response => location.reload())                  
                  .catch(res=> Swal.fire(res.response.data,"Error en los datos","error"))              
                }
              })   
        },
        enviarCampaniasPorMailOpcion1(){
            let formData = new FormData();
            formData.append('file', this.file);
            Swal.fire({
                title: '¿Desea enviar solicitud de campaña?',
                text: "Se enviará un correo a todos los referentes del archivo.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                cancelButtonText: "No!",
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/enviarCampaniasPorMailOpcionEnvio1',
                    "campaniaSeleccionada="+this.campaniaSeleccionada+"&"+"asuntoMail="+this.asuntoMail+"&"+
                    "cuerpoMail="+this.cuerpoMail+"&"+"referentesSeleccionadosArr="+this.referentesSeleccionadosArr,                                
                    {headers:{'accept':'application/xml'}})
                    .then(res => Swal.fire('Solicitud enviada con éxito!','','success',))
                    .then(res => location.reload())                                   
                  .catch(res=> Swal.fire(res.response.data,"Error en los datos","error"))              
                }
              })  
        },

        agregarReferente(referente){                
          this.referentesSeleccionadosArr.push(referente.nombre)
        },

        enviarCampaniasPorMailOpcionEnvio2(){
         Swal.fire({
             title: '¿Desea enviar solicitud de campaña?',
             text: "Se enviará un correo al referente ingresado.",
             icon: 'warning',
             showCancelButton: true,
             confirmButtonColor: '#3085d6',
             cancelButtonColor: '#d33',
             cancelButtonText: "No!",
             confirmButtonText: 'Si!'
           }).then((result) => {
             if (result.isConfirmed) {
                 axios.post('/api/enviarCampaniasPorMailOpcionEnvio2',
                 "campaniaSeleccionada="+this.campaniaSeleccionada+"&"+"asuntoMail="+this.asuntoMail+"&"+
                 "cuerpoMail="+this.cuerpoMail+"&"+"mailReferente="+this.mailReferente+"&"+"nombreReferente="+this.nombreReferente,                                
                 {headers:{'accept':'application/xml'}})
                 .then(res => Swal.fire('Solicitud enviada con éxito!','','success',))
                 .then(res => location.reload())                                   
               .catch(res=> Swal.fire(res.response.data,"Error en los datos","error"))              
             }
           })  
     },
        
       
        generarPDF(campania){
            axios.post("/api/campania/export/pdf", "tituloCampania=" + campania.titulo,
             { responseType: 'blob' })
                .then((response) => {
                    const url = window.URL.createObjectURL(new Blob([response.data]));
                    const link = document.createElement('a');
                    link.href = url;
                    link.setAttribute('download', 'Detalles de'+" "+ campania.titulo + '.pdf');
                    document.body.appendChild(link);
                    link.click();     
                    Swal.fire('Se inicio descarga de .PDF!','','success',)             
                })
                .catch(err => {
                    Swal.fire(res.response.data,"Error al generar el .PDF","error")
                })    

        },

        filtrarReferente(){            
          axios.get('/api/referenteFiltro',
            {params: {
                filtro: this.aFiltrarReferente
            },
                headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(res=>{
              this.referentes= res.data})                          
            .catch(res => Swal.fire("Error","No se pudo realizar la consulta.","error"))
        },

        resetReferente(){
          axios.get('/api/referentes')
          .then(res=> {
            this.referentes = res.data 
          })
        },

      filtrarCampaniaReferente(){            
        axios.get('/api/campaniaReferenteFiltro',
           {params: {              
              filtroCampania:this.campaniaReferenteFiltroCampaniaSeleccionada,
              filtroReferente: this.campaniaReferenteFiltroPorReferente 
           },
           headers:{'content-type':'application/x-www-form-urlencoded'}})
          .then(res=>{
            this.campaniasReferentes= res.data})                          
          .catch(res => Swal.fire("Error","No se pudo realizar la consulta.","error"))
      },

      resetCampaniaReferente(){        
          this.campaniasReferentes = ''   
          this.campaniaReferenteFiltroCampaniaSeleccionada=''
          this.campaniaReferenteFiltroPorReferente=''     
      },

      buscarReferente(){
        axios.get('/api/buscarReferente',
            {params: {
                emailReferente: this.mailReferente
            },
                headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(res=>{
              this.nombreRefeenteEncontrado= res.data
              this.nombreReferente=this.nombreRefeenteEncontrado
              if(this.nombreRefeenteEncontrado=='')
              {
                this.nombreRefeenteEncontrado='vacio'              
                Swal.fire("Proveedor no encontrado","Ingrese un nombre de proveedor.","info")              
              }
            })                          
            .catch(res => Swal.fire("error","No se pudo realizar la consulta.","error"))

    },
      

        /*
        Validacion de archivo .TXT ingresado en envio de campañas
        */
        validarArchivoInput(){
        let formData = new FormData();
        formData.append('file', this.file);
        Swal.fire({
          title: '¿Validar archivo?',
          text: "",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          cancelButtonText: "No!",
          confirmButtonText: 'Si!'
        }).then((result) => {
          if (result.isConfirmed) {
              axios.post("/api/validarArchivoInput",
              formData,                                
              {headers:{'Content-Type': 'multipart/form-data'}})
              .then(res =>{
                 this.referentesSeleccionadosArr=res.data;                   
                 if(this.referentesSeleccionadosArr[0]=='0')
                  {
                    Swal.fire("Archivo contiene errores de formato o caracteres no válidos","",'error')
                    var archivoInput = document.getElementById('file');
                    archivoInput.value = '';
                    this.archivoValidado='';
                  }
                  else{    
                    console.log(this.referentesSeleccionadosArr)
                    this.archivoValidado='si';               
                    Swal.fire("Archivo validado con éxito","",'success')                 
                  }                
                })       
               .catch(res=>{
                Swal.fire("Archivo contiene errores de formato o caracteres no válidos","",'error')
                var archivoInput = document.getElementById('file');
                archivoInput.value = '';
                }) 
          }
        })  
  },

      /*
        Validacion EXTENSION de  archivo ingresado en envio de campañas ( debe ser .txt)
      */     
        validarExtensionArchivoEnvioCampanias(){
        var archivoInput = document.getElementById('file');
        var archivoRuta=archivoInput.value;
        var extPermitidas = /(.csv|.CSV)$/i;
        if(!extPermitidas.exec(archivoRuta)){
            Swal.fire("","Asegurese de haber seleccionado un archivo '.CVS'","info")
            archivoInput.value = '';
            return false;
        }
        else
        {
        this.file = this.$refs.file.files[0];
        }
      },

      /*
        Validacion EXTENSION de  archivo ingresado en agregar Campañas ( debe ser .pdf)
      */
        validarExtensionArchivoAgregarCampania(){
        var archivoInputPDF = document.getElementById('fileCampania');
        var archivoRuta=archivoInputPDF.value;
        var extPermitidas = /(.pdf|.PDF)$/i;
        if(!extPermitidas.exec(archivoRuta)){
            Swal.fire("","Asegurese de haber seleccionado un archivo '.pdf'","info")
            archivoInputPDF.value = '';
            return false;
        }
        else
        {
        this.fileCampania = this.$refs.fileCampania.files[0];
        }
      },

      previsualizacionOk(){       
       this.previsualizacion='si'        
      },

      previsualizacionNo(){       
        this.previsualizacion='no'        
       },

      formatDate(date){
        if(date==null)
        {
          return "Sin registros"
        }
        else
        {
        let date1 = new Date(date).toString()      
        return date1.slice(0,24)
        }
    },

},
    computed: {      
    }, 
})
app.mount("#app")

