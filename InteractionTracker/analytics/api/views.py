"""
 # Interaction Tracker
 # @license http://www.apache.org/licenses/LICENSE-2.0
 # Author @ Jamil Hussain, Zaki
"""


from rest_framework import viewsets
from analytics.api.serializers import (ActionLogSerializer)
from analytics.models import(ActionLog)

#Api View for User Analytics Action Log
class ActionLogViewSet(viewsets.ModelViewSet):
    queryset = ActionLog.objects.all()
    serializer_class = ActionLogSerializer




