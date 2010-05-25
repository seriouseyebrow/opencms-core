/*
 * File   : $Source: /alkacon/cvs/opencms/src-modules/org/opencms/ade/galleries/shared/Attic/CmsGalleryFolderBean.java,v $
 * Date   : $Date: 2010/05/19 09:02:51 $
 * Version: $Revision: 1.3 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (C) 2002 - 2009 Alkacon Software (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.ade.galleries.shared;

import org.opencms.gwt.shared.sort.I_CmsHasPath;
import org.opencms.gwt.shared.sort.I_CmsHasTitle;
import org.opencms.gwt.shared.sort.I_CmsHasType;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A specific bean holding all info to be displayed in {@link org.opencms.ade.galleries.client.ui.CmsGalleryListItem}s.<p>
 * 
 * @see org.opencms.ade.galleries.client.ui.CmsGalleryListItem
 * 
 * @author Polina Smagina
 * 
 * @version $Revision: 1.3 $
 * 
 * @since 8.0.0
 */
public class CmsGalleryFolderBean implements I_CmsHasTitle, I_CmsHasPath, I_CmsHasType, IsSerializable {

    /** A list with content types corresponding to this gallery type. */
    private ArrayList<String> m_contentTypes;

    /** The gallery type name. */
    private String m_type;

    private String m_title;

    private String m_path;

    /**
     * Returns the content types which can be used within this gallery type.<p>
     *
     * @return the contentTypes
     */
    public ArrayList<String> getContentTypes() {

        return m_contentTypes;
    }

    /**
     * Returns the gallery type name.<p>
     *
     * @return the gallery type name
     */
    public String getType() {

        return m_type;
    }

    /**
     * Sets the content types which can be used within this gallery type.<p>
     *
     * @param contentTypes the contentTypes to set
     */
    public void setContentTypes(ArrayList<String> contentTypes) {

        m_contentTypes = contentTypes;
    }

    /**
     * Sets the gallery type name.<p>
     *
     * @param type the type name of this gallery
     */
    public void setType(String type) {

        m_type = type;
    }

    /**
     * Returns the title.<p>
     *
     * @return the title
     */
    public String getTitle() {

        return m_title;
    }

    /**
     * Sets the title.<p>
     *
     * @param title the title to set
     */
    public void setTitle(String title) {

        m_title = title;
    }

    /**
     * Returns the description.<p>
     *
     * @return the description
     */
    public String getPath() {

        return m_path;
    }

    /**
     * Sets the description.<p>
     *
     * @param path the description to set
     */
    public void setPath(String path) {

        m_path = path;
    }
}