<template>
    <div>
        <q-card class="signup-card ac mt-9 p15">
            <q-btn color="primary" class="ac" @click="backStepTable">Voltar</q-btn>
            <h4 class="mb-2 mt-2">Alterar Usuário</h4>
            
            <q-input :error="newUserError.name" outlined class="mt-2" v-model="newUser.name" label="Nome" />
            <q-input :error="newUserError.email" :rules="emailRules" outlined class="mt-0" v-model="newUser.email" label="Email" />

            <!-- <q-input :error="newUserError.password" outlined v-model="newUser.password" :type="isPwd ? 'password' : 'text'" label="Senha">
                <template v-slot:append>
                <q-icon
                    :name="isPwd ? 'visibility_off' : 'visibility'"
                    class="cursor-pointer"
                    @click="isPwd = !isPwd"
                />
                </template>
            </q-input> -->

            <!-- <q-input :error="newUserError.document" :mask="'###.###.###-##'" outlined class="mt-0" v-model="newUser.document" label="cpf" /> -->
        

                <div class="column items-end mt-3">
                    <q-btn color="primary" class="ac" @click="updateUser">Cadastrar</q-btn>
                </div>
        </q-card>
    </div>
</template>
<script>
export default {
    props:['propsData'],

    data:() => ({
        url: 'http://localhost:8080/api',
        isPwd: true,
        newUser:{
            name:'',
            email:'',
            password:'',
            document:''
        },

        newUserError:{
            name: false,
            email: false,
            password: false,
            document: false,
        },

        emailRules: [
            v => !!v || 'Precisa preencher.',
            v => /.+@.+/.test(v) || 'E-mail precisa se válido',
        ],
    }),

    methods:{
        validateInputs(){
            if( this.newUser.name == ''){this.newUserError.name = true}
            if( this.newUser.email == ''){this.newUserError.email = true}
            if( this.newUser.password == ''){this.newUserError.password = true}
            if( this.newUser.document == ''){this.newUserError.document = true}
        },
        validateUpdateInputs(){
            if( this.newUser.name == ''){this.newUserError.name = true}
            if( this.newUser.email == ''){this.newUserError.email = true}
        },

        cleanFields(){
            localStorage.removeItem('updateUserData');
            this.newUser.name = ''
            this.newUser.email = ''
            this.newUser.password = ''
            this.newUser.document = ''
        },

        backStepTable(){
            this.$emit('backStep')
            localStorage.removeItem('updateUserData');
        },

        updateUser(){
            this.validateUpdateInputs()
            if(
                this.newUserError.name == false 
                && this.newUserError.email == false
            ){
                console.log(this.newUser)
                let userId = this.propsData.id
                let body = {
                    name: this.newUser.name,
                    email: this.newUser.email
                }
                this.$http.put(this.url + `/user/${userId}`, body)
                .then(resp =>{
                    // console.log(resp)
                    if(resp.status == 200){
                        // emit
                        this.backStepTable()
                        this.cleanFields()
                        this.$q.notify({ position: 'bottom-right', message: 'Usuário Alterado com sucesso', color: 'green' })
                    }
                })
                .catch(err =>{
                    console.log(err)
                })
            }
        }
    },

    created(){
        if(this.propsData){
            this.newUser.name = this.propsData.name
            this.newUser.email = this.propsData.email
            this.newUser.document = this.propsData.document
        }
    },

    mounted() {
        let userDataLocal = JSON.parse(localStorage.getItem('updateUserData'));

        if(userDataLocal){

            if ( 
                userDataLocal.name ||
                userDataLocal.email ||
                userDataLocal.password ||
                userDataLocal.document 
            ) 
            {
                this.newUser.name = userDataLocal.name;
                this.newUser.email = userDataLocal.email;
                this.newUser.password = userDataLocal.password;
                this.newUser.document = userDataLocal.document;

            }

        }

    },

    watch: {
        newUser:{
            deep: true,
            handler: function(newData) {
                if(newData.name != ''){
                    this.newUserError.name = false
                }
                if(newData.email != ''){
                    this.newUserError.email = false
                }
                if(newData.password != ''){
                    this.newUserError.password = false
                }
                if(newData.document != ''){
                    this.newUserError.document = false
                }
                localStorage.setItem('updateUserData', JSON.stringify(newData));
            }

        }
    }
}
</script>
<style>
    .signup-card{
        max-width: 35%;
        width: 100%;
    }
</style>