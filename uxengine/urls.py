"""
 # Interaction Tracker
 # @license http://www.apache.org/licenses/LICENSE-2.0
 # Author @ Jamil Hussain, Zaki
"""


from django.conf.urls import url, include
from django.contrib import admin

from . import views

urlpatterns = [

    url(r'^$', views.homeView, name='home'),
    url(r'^api/', include("analytics.api.urls", namespace='analytics-api')),
    url(r'^api/users/', include("accounts.api.urls", namespace='users-api')),
    url(r'^admin/', admin.site.urls),

    url(r'^analytics/', include('analytics.urls')),
    url(r'^facialexpressions/', include('facialexpressions.urls')),
    url(r'^accounts/', include('accounts.urls')),
    url(r'^survey/', include('survey.urls')),
    url(r'^sentimentanalyzer/', include('sentimentanalyzer.urls')),
    url(r'^emovoice/', include('emovoice.urls')),

]

