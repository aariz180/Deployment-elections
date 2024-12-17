import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/LoginPage.vue'
import Register from '@/views/RegisterPage.vue'
import HelloWorld from '@/views/HelloWorld.vue'
import ForumOverview from '@/views/ForumOverview.vue'
import UpdateAccountPage from '@/views/UpdateAccountPage.vue'
import PartyOverview from '@/views/PartyOverview.vue'
import AccountPage from '@/views/AccountPage.vue'
import PartyInformation from '@/views/PartyInformation.vue'
import PageNotFound from '@/views/PageNotFound.vue'
import PPWizard from '@/views/PPWizard.vue'
import CreateForum from '@/views/ForumForm.vue'
import MapPage from '@/views/MapPage.vue'
import ForumDiscussion from '@/views/ForumDiscussion.vue'

const routes = [
    {
        path: '/register',
        name: 'Register',
        component: Register,
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
        path: '/',
        name: 'HelloWorld',
        component: HelloWorld,
    },
    {
        path: '/ForumOverview',
        name: 'ForumOverview',
        component: ForumOverview,
    },
    {
        path: '/partyOverview',
        name: 'PartyOverview',
        component: PartyOverview,
    },
    {
        path: '/parties/:partyName',
        name: 'PartyInformation',
        component: PartyInformation,
    },
    {
        path: '/accountPage',
        name: 'AccountPage',
        component: AccountPage,
    },
    {
        path: '/:catchAll(.*)',
        name: 'NotFound',
        component: PageNotFound,
    },
    {
        path: '/updateAccountPage',
        name: 'UpdateAccountPage',
        component: UpdateAccountPage,
    },
    {
        path: '/PPWizard',
        name: 'PPWizard',
        component: PPWizard,
    },
    {
        path: '/CreateForum',
        name: 'CreateForum',
        component: CreateForum,
    },
    {
        path: '/MapPage',
        name: 'MapPage',
        component: MapPage,
    },
    {
        path: '/ForumDiscussion/:forumId/:forumTitle',
        name: 'ForumDiscussion',
        component: ForumDiscussion,
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
