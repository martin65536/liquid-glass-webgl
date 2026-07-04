package org.jetbrains.compose.resources;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import defpackage.v7;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class AndroidContextProvider extends ContentProvider {
    public static Context e;

    @Override // android.content.ContentProvider
    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        context.getClass();
        if (providerInfo != null) {
            if (!"org.jetbrains.compose.components.resources.resources.AndroidContextProvider".equals(providerInfo.authority)) {
                super.attachInfo(context, providerInfo);
                return;
            } else {
                v7.o("Incorrect provider authority in manifest. Most likely due to a missing applicationId variable your application's build.gradle.");
                return;
            }
        }
        throw new NullPointerException("AndroidContextProvider ProviderInfo cannot be null.");
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        uri.getClass();
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        uri.getClass();
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        uri.getClass();
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        e = getContext();
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        uri.getClass();
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        uri.getClass();
        return 0;
    }
}
