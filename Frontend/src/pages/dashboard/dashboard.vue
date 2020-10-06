<template>
    <q-page>
        <div>
            <div v-if="step == 'updateUser'" class="ac">
                <FormFields :propsData="selectedUser" @backStep="backToStepTable"/>
            </div>

            <q-card v-if="step == 'userTable'" class="table-card ac mt-7">
                <q-btn color="primary" to="/Signup" class="ml-2 mt-2">Adicionar</q-btn>

                <div class="q-pa-md">
                    <q-table
                    title="Treats"
                    :data="dataTable"
                    :columns="columns"
                    row-key="id"
                    >
                    
                        <template v-slot:body-cell-actions="props">
                            <q-td :props="props">
                                <div>
                                    <!-- <q-badge color="purple" :label="props.value"></q-badge> -->
                                    <q-btn color="red" @click="deleteUser(props.key)">deletar</q-btn>
                                    <q-btn class="ml-2" color="primary" @click="updateUser(props.key)">Atualizar</q-btn>
                                </div>
                            </q-td>
                        </template>

                    </q-table>
                </div>
            </q-card>
        </div>
    </q-page>
</template>
<script>
import FormFields from '../../components/formFields'
export default {
    components:{
        FormFields
    },
    
    data:() => ({
        url: 'http://localhost:8080/api',

        step:'userTable',

        selectedUser:'',

        columns:[
            { name: 'id', align: 'center', label: 'id', field: 'id', sortable: true },
            { name: 'name', align: 'center', label: 'Nome', field: 'name', sortable: true },
            { name: 'email', align: 'center', label: 'email', field: 'email', sortable: true },
            { name: 'document', align: 'center', label: 'CPF', field: 'document', sortable: true },
            { name: 'actions', align: 'center', label: 'Ações', field: 'actions', sortable: true },
        ],

        data: [
            { name: 'Pedro Lopes', id: 1, email: "teste@teste.com", document: "123456789", actions:''},
        ],

        dataTable:[]
    }),

    created(){
        // console.log(process.env.DEV)
        this.listUsers()
    },

    methods:{
        updateUser(param){
            let userId = param
            this.$http.get(this.url + `/user/${userId}`)
            .then(resp => {
                this.$q.notify({ position: 'bottom-right', message: 'Usuário encontrado com sucesso', color: 'green' })
                console.log(resp)
                this.selectedUser = resp.data
                this.step = 'updateUser'
            })
            .catch(err =>{
                alert(err)
                this.$q.notify({ position: 'bottom-right', message: 'Ops! algo deu errado, tente novamente!', color: 'red' })
                console.log(err)
            })
        },

        backToStepTable(){ 
            this.listUsers()
            this.step = 'userTable' 
        },
    
        deleteUser(param){
            let userId = param
            this.$http.delete(this.url + `/user/${userId}`)
            
            .then(resp => {
                this.$q.notify({ position: 'bottom-right', message: 'Usuário Deletado com sucesso', color: 'green' })
                this.listUsers()
                console.log(resp)
            })

            .catch(err =>{
                alert(err)
                this.$q.notify({ position: 'bottom-right', message: 'Ops! algo deu errado, tente novamente!', color: 'red' })
                console.log(err)
            })

        },

        listUsers(){
            this.$http.get(this.url + '/user')
            .then(resp => {
                this.dataTable = resp.data
            })
        }
    }
}
</script>

<style>
    .table-card{
        max-width: 80%;
        width: 100%;
    }
</style>