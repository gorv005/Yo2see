package com.appiness.yo2see.callbacks



interface AuthFragmentInterface {
   /* fun onSignUpSuccessful(response: AuthenticationResponse?)
    fun onSignInSuccessful(response: AuthenticationResponse?)
*/
    fun setSignInFragment()
    fun setSignUpFragment()

    fun onSkipClick()
}