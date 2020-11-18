#include <jni.h>
#include <windows.h>
#include "xyz_dreeks_audhumla_jni_JNIVibrancyWindows.h"

JNIEXPORT jint JNICALL Java_xyz_dreeks_audhumla_jni_JNIVibrancyWindows_SetWindowAcrylic(JNIEnv *env, jobject obj, jstring windowName) {
	const char *wName = (*env).GetStringUTFChars(windowName, 0);
	HWND launcher = FindWindow(NULL, wName);
	if (launcher == 0) {
		return 1;
	}

	const HINSTANCE hModule = LoadLibrary(TEXT("user32.dll"));
	if (hModule)
	{
		struct ACCENTPOLICY
		{
			int nAccentState;
			int nFlags;
			int nColor;
			int nAnimationId;
		};
		struct WINCOMPATTRDATA
		{
			int nAttribute;
			PVOID pData;
			ULONG ulDataSize;
		};
		typedef BOOL(WINAPI*pSetWindowCompositionAttribute)(HWND, WINCOMPATTRDATA*);
		const pSetWindowCompositionAttribute SetWindowCompositionAttribute = (pSetWindowCompositionAttribute)GetProcAddress(hModule, "SetWindowCompositionAttribute");
		if (SetWindowCompositionAttribute)
		{
			ACCENTPOLICY policy = { 3, 0, 0, 0 }; // ACCENT_ENABLE_BLURBEHIND=3...
			WINCOMPATTRDATA data = { 19, &policy, sizeof(ACCENTPOLICY) }; // WCA_ACCENT_POLICY=19
			SetWindowCompositionAttribute(launcher, &data);
		}
		FreeLibrary(hModule);
	}

	return 0;
}