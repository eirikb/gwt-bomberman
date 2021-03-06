/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@eirikb.no> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ----------------------------------------------------------------------------
 */
package no.eirikb.bomberman.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ClientBundle.Source;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;

/**
 *
 * @author Eirik Brandtzæg <eirikb@eirikb.no>
 */
public interface GameResouces extends ClientBundle {

    @Source("md1.png")
    @ImageOptions(flipRtl = true)
    ImageResource manDown1();
}
