package defpackage;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import java.util.concurrent.ExecutorService;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cu {
    public final /* synthetic */ int a;
    public final ContentProviderClient b;

    public cu(Context context, Uri uri, int i) {
        this.a = i;
        switch (i) {
            case 1:
                this.b = context.getContentResolver().acquireUnstableContentProviderClient(uri);
                return;
            default:
                this.b = context.getContentResolver().acquireUnstableContentProviderClient(uri);
                return;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a() {
        int i = this.a;
        ContentProviderClient contentProviderClient = this.b;
        switch (i) {
            case 0:
                if (contentProviderClient != 0) {
                    contentProviderClient.release();
                    return;
                }
                return;
            default:
                if (contentProviderClient != 0) {
                    if (contentProviderClient instanceof AutoCloseable) {
                        contentProviderClient.close();
                        return;
                    } else if (contentProviderClient instanceof ExecutorService) {
                        x0.n((ExecutorService) contentProviderClient);
                        return;
                    } else {
                        contentProviderClient.release();
                        return;
                    }
                }
                return;
        }
    }
}
