"""
 # Interaction Tracker
 # @license http://www.apache.org/licenses/LICENSE-2.0
 # Author @ Jamil Hussain, Zaki
"""

from django import forms
from .models import App


class AppCreationForm(forms.ModelForm):
    #A form for creating new APP. Includes all the requiredfields
    app_name = forms.CharField()
    class Meta:
        model = App
        fields = ('app_name','id')
       
    # this function save the data sumited by user form
    # Base on commit values, if commit ture then it save the form.
    def save(self, commit=True):
        app = super(AppCreationForm, self).save(commit=False)
        if commit:
            app.save()
        return app





